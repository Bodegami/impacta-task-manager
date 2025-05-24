import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import {
  fetchTasksFromBackend,
  fetchTaskDetails,
  updateTask,
  fetchTasksByFilter,
} from "../services/taskService";
import { formatarDataHora } from "../utils/dateUtils";
import TaskCard from "../components/TaskCard";
import "./TaskListPage.css";

// dnd-kit imports
import { DndContext, closestCorners } from "@dnd-kit/core";
import { SortableContext, verticalListSortingStrategy } from "@dnd-kit/sortable";

export default function TaskListPage() {
  const navigate = useNavigate();
  const [tasks, setTasks] = useState([]);
  const [expandedTaskId, setExpandedTaskId] = useState(null);
  const [selectedTaskDetails, setSelectedTaskDetails] = useState(null);
  const [loadingDetails, setLoadingDetails] = useState(false);
  const [detailsError, setDetailsError] = useState(null);
  const [updatingStatus, setUpdatingStatus] = useState(false);
  const [updateStatusError, setUpdateStatusError] = useState(null);

  const [filtroCampo, setFiltroCampo] = useState("taskId");
  const [filtroValor, setFiltroValor] = useState("");

  const COLUMN_STATUSES = ["BACKLOG", "TO_DO", "IN_PROGRESS", "DONE"];

  const [columns, setColumns] = useState({
    BACKLOG: [],
    TO_DO: [],
    IN_PROGRESS: [],
    DONE: [],
  });

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const data = await fetchTasksFromBackend();
        setTasks(data);

        const organized = {
          BACKLOG: [],
          TO_DO: [],
          IN_PROGRESS: [],
          DONE: [],
        };

        data.forEach((task) => {
          const status = task.status;
          if (organized[status]) {
            organized[status].push(task);
          }
        });

        setColumns(organized);
      } catch (error) {
        alert(error.message || "Erro ao conectar com o servidor.");
      }
    };

    fetchTasks();
  }, []);

  const toggleExpand = async (taskId) => {
    if (expandedTaskId === taskId) {
      setExpandedTaskId(null);
      setSelectedTaskDetails(null);
    } else {
      setExpandedTaskId(taskId);
      setLoadingDetails(true);
      setDetailsError(null);
      try {
        const data = await fetchTaskDetails(taskId);
        setSelectedTaskDetails(data);
      } catch (error) {
        setDetailsError("Erro ao buscar detalhes da tarefa.");
      } finally {
        setLoadingDetails(false);
      }
    }
  };

  const handleStatusChange = async (taskId, title, description, dueDate, status) => {
    setUpdatingStatus(true);
    setUpdateStatusError(null);

    setExpandedTaskId(null);
    setSelectedTaskDetails(null);

    try {
      dueDate = formatarDataHora(dueDate, "-");

      const taskData = {
        title,
        description,
        dueDate,
        status,
      };

      await updateTask(taskId, taskData);
      await refetchAndOrganizeTasks();

      setTasks((prevTasks) =>
        prevTasks.map((task) =>
          task.id === taskId ? { ...task, status: status } : task
        )
      );

      setSelectedTaskDetails((prevDetails) => ({
        ...prevDetails,
        status: status,
      }));
    } catch (error) {
      setUpdateStatusError("Erro ao atualizar o status.");
    } finally {
      setUpdatingStatus(false);
    }
  };

  const refetchAndOrganizeTasks = async () => {
    try {
      const data = await fetchTasksFromBackend();
      setTasks(data);

      const organized = {
        BACKLOG: [],
        TO_DO: [],
        IN_PROGRESS: [],
        DONE: [],
      };

      data.forEach((task) => {
        const status = task.status;
        if (organized[status]) {
          organized[status].push(task);
        }
      });

      setColumns(organized);
    } catch (error) {
      alert(error.message || "Erro ao conectar com o servidor.");
    }
  };

  const buscarTarefasFiltradas = async () => {
    if (!filtroValor.trim()) {
      alert("Digite um valor para buscar.");
      return;
    }

    try {
      const data = await fetchTasksByFilter(filtroCampo, filtroValor);
      setTasks(data);
      setExpandedTaskId(null);
      setSelectedTaskDetails(null);

      const novoMapeamento = {
        BACKLOG: [],
        TO_DO: [],
        IN_PROGRESS: [],
        DONE: [],
      };

      data.forEach((task) => {
        const status = task.status;
        if (novoMapeamento[status]) {
          novoMapeamento[status].push(task);
        }
      });

      setColumns(novoMapeamento);
    } catch (error) {
      alert(error.message || "Erro ao filtrar tarefas.");
    }
  };

  const limparFiltro = async () => {
    setFiltroValor("");
    try {
      const data = await fetchTasksFromBackend();
      setTasks(data);
      setExpandedTaskId(null);
      setSelectedTaskDetails(null);

      const novoMapeamento = {
        BACKLOG: [],
        TO_DO: [],
        IN_PROGRESS: [],
        DONE: [],
      };

      data.forEach((task) => {
        if (novoMapeamento[task.status]) {
          novoMapeamento[task.status].push(task);
        }
      });

      setColumns(novoMapeamento);
    } catch (error) {
      alert("Erro ao recarregar tarefas.");
    }
  };

  const camposFiltro = [
    { label: "ID", value: "taskId" },
    { label: "Título", value: "titulo" },
    { label: "Descrição", value: "descricao" },
    { label: "Status", value: "status" },
  ];

  // Função de drag and drop corrigida
  const handleDragEnd = async (event) => {
    const { active, over } = event;
    if (!over || active.id === over.id) return;

    // Descobre de qual status para qual status está indo
    let origemStatus = null;
    COLUMN_STATUSES.forEach((status) => {
      if (columns[status].find((t) => t.id === active.id)) origemStatus = status;
    });

    const destinoStatus = over.id;

    // Se o card foi arrastado para outra coluna
    if (
      origemStatus &&
      destinoStatus &&
      origemStatus !== destinoStatus &&
      COLUMN_STATUSES.includes(destinoStatus)
    ) {
      const tarefaMovida = columns[origemStatus].find((t) => t.id === active.id);
      if (!tarefaMovida) return;

      await handleStatusChange(
        tarefaMovida.id,
        tarefaMovida.title,
        tarefaMovida.description,
        tarefaMovida.dueDate || tarefaMovida.due_date,
        destinoStatus
      );
    }
  };

  return (
    <>
      <div className="tasklist-bg-image"></div>
      <div className="tasklist-main-container">
        <div className="tasklist-bg-overlay"></div>
        <div className="tasklist-content">
          <h1 className="tasklist-title">Quadro de Tarefas</h1>

          <div className="tasklist-filter-box">
            <h3 style={{ marginBottom: "10px" }}>Filtrar Tarefas</h3>
            <div className="tasklist-filter-row">
              <select
                value={filtroCampo}
                onChange={(e) => setFiltroCampo(e.target.value)}
                className="tasklist-filter-select"
              >
                {camposFiltro.map((campo) => (
                  <option key={campo.value} value={campo.value}>
                    {campo.label}
                  </option>
                ))}
              </select>
              <input
                type="text"
                placeholder="Digite o valor"
                value={filtroValor}
                onChange={(e) => setFiltroValor(e.target.value)}
                className="tasklist-filter-input"
              />
              <button
                onClick={buscarTarefasFiltradas}
                className="tasklist-filter-btn tasklist-filter-btn-green"
              >
                Consultar
              </button>
              <button
                onClick={limparFiltro}
                className="tasklist-filter-btn tasklist-filter-btn-red"
              >
                X
              </button>
            </div>
          </div>

          <div className="tasklist-actions-row">
            <button
              onClick={() => navigate("/tasks/create")}
              className="tasklist-action-btn"
            >
              Criar Tarefa
            </button>
            <button
              onClick={() => navigate("/")}
              className="tasklist-action-btn"
            >
              Voltar para Home
            </button>
          </div>

          {/* QUADRO DE TAREFAS COM LINHAS VERTICAIS E DRAG AND DROP */}
          <DndContext collisionDetection={closestCorners} onDragEnd={handleDragEnd}>
            <div className="tasklist-board">
              {[1, 2, 3].map((i) => (
                <div
                  key={i}
                  className="tasklist-board-divider"
                  style={{
                    left: `calc(${(i * 100) / 4}% - 2px)`,
                  }}
                />
              ))}

              {COLUMN_STATUSES.map((status) => (
                <SortableContext
                  key={status}
                  id={status}
                  items={columns[status].map((t) => t.id)}
                  strategy={verticalListSortingStrategy}
                >
                  <div className="tasklist-board-column" id={status}>
                    <span className="tasklist-board-column-title">
                      {status.replace(/_/g, " ").toUpperCase()}
                    </span>
                    <div className="tasklist-board-cards">
                      {columns[status].length === 0 ? (
                        <p className="tasklist-board-empty">Sem tarefas</p>
                      ) : (
                        columns[status].map((task) => (
                          <TaskCard
                            key={task.id}
                            task={task}
                            isExpanded={expandedTaskId === task.id}
                            selectedTaskDetails={selectedTaskDetails}
                            loadingDetails={loadingDetails}
                            detailsError={detailsError}
                            updatingStatus={updatingStatus}
                            updateStatusError={updateStatusError}
                            toggleExpand={toggleExpand}
                            handleStatusChange={handleStatusChange}
                            onDelete={() =>
                              setTasks((prev) => prev.filter((t) => t.id !== task.id))
                            }
                          />
                        ))
                      )}
                    </div>
                  </div>
                </SortableContext>
              ))}
            </div>
          </DndContext>
        </div>

        <footer className="tasklist-footer">
          Projeto de Software Impacta - 2025 | Renato Ferreira - devbodegami@gmail.com
        </footer>
      </div>
    </>
  );
}