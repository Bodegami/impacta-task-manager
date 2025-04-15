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
import { DndContext, closestCorners } from "@dnd-kit/core";
import {
  arrayMove,
  SortableContext,
  verticalListSortingStrategy,
} from "@dnd-kit/sortable";
import DroppableColumn from "../components/DroppableColumn";
import { SiGooglesearchconsole } from "react-icons/si";

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
        console.error("Erro ao buscar detalhes:", error);
        setDetailsError("Erro ao buscar detalhes da tarefa.");
      } finally {
        setLoadingDetails(false);
      }
    }
  };

  const handleStatusChange = async (taskId, title, description, dueDate, status) => {
    setUpdatingStatus(true);
    setUpdateStatusError(null);

      // Fecha expansão antes de atualizar
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

      console.log("Tarefa atualizada com sucesso:", taskId, taskData);
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
      console.error("Erro ao atualizar status:", error);
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

  const handleDragEnd = async (event) => {
    const { active, over } = event;
    if (!over || active.id === over.id) return;

    const origemStatus = active.data.current?.status;
    const destinoStatus = over.id;

    if (!origemStatus || !destinoStatus || origemStatus === destinoStatus) return;

    const tarefaMovida = columns[origemStatus].find((task) => task.id === active.id);

    if (!tarefaMovida) return;

    try {
      await handleStatusChange(
        tarefaMovida.id,
        tarefaMovida.title,
        tarefaMovida.description,
        tarefaMovida.dueDate,
        destinoStatus
      );

      setColumns((prev) => {
        const novaOrigem = prev[origemStatus].filter((t) => t.id !== tarefaMovida.id);
        const novoDestino = [tarefaMovida, ...prev[destinoStatus]];

        return {
          ...prev,
          [origemStatus]: novaOrigem,
          [destinoStatus]: novoDestino,
        };
      });
    } catch (error) {
      alert("Erro ao mover tarefa.");
    }
  };

  return (
    <>
      <div
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          width: "100%",
          height: "100%",
          backgroundImage: `url(/assets/post-its.jpeg)`,
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundAttachment: "fixed",
          zIndex: -1,
        }}
      ></div>

      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          minHeight: "100vh",
          position: "relative",
          width: "100%",
        }}
      >
        <div
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: "rgba(255, 255, 255, 0.8)",
            zIndex: 0,
          }}
        ></div>

        <div
          style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            flexGrow: 1,
            zIndex: 1,
            width: "95%",
          }}
        >
          <h1 style={{ fontSize: "2.5rem", marginBottom: "10px", color: "#333" }}>
            Quadro de Tarefas
          </h1>

          <div
            style={{
              marginTop: "10px",
              backgroundColor: "#f5f5f5",
              padding: "20px",
              borderRadius: "10px",
              width: "100%",
              boxShadow: "0 0 10px rgba(0,0,0,0.1)",
            }}
          >
            <h3 style={{ marginBottom: "10px" }}>Filtrar Tarefas</h3>
            <div style={{ display: "flex", alignItems: "center", gap: "10px", flexWrap: "wrap" }}>
              <select
                value={filtroCampo}
                onChange={(e) => setFiltroCampo(e.target.value)}
                style={{ padding: "8px", borderRadius: "5px", minWidth: "120px" }}
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
                style={{ padding: "8px", borderRadius: "5px", flexGrow: 1, minWidth: "200px" }}
              />
              <button
                onClick={buscarTarefasFiltradas}
                style={{
                  padding: "8px 16px",
                  backgroundColor: "#2ecc71",
                  color: "white",
                  border: "none",
                  borderRadius: "5px",
                  cursor: "pointer",
                }}
              >
                Consultar
              </button>
              <button
                onClick={limparFiltro}
                style={{
                  padding: "8px 16px",
                  backgroundColor: "#e74c3c",
                  color: "white",
                  border: "none",
                  borderRadius: "5px",
                  cursor: "pointer",
                }}
              >
                X
              </button>
            </div>
          </div>

          <div style={{ marginTop: "20px", display: "flex", gap: "10px" }}>
            <button
              onClick={() => navigate("/tasks/create")}
              style={{
                padding: "10px 20px",
                borderRadius: "5px",
                border: "none",
                backgroundColor: "#3498db",
                color: "white",
                cursor: "pointer",
              }}
            >
              Criar Tarefa
            </button>
            <button
              onClick={() => navigate("/")}
              style={{
                padding: "10px 20px",
                borderRadius: "5px",
                border: "none",
                backgroundColor: "#3498db",
                color: "white",
                cursor: "pointer",
              }}
            >
              Voltar para Home
            </button>
          </div>

          <DndContext collisionDetection={closestCorners} onDragEnd={handleDragEnd}>
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "start",
                width: "100%",
                marginTop: "20px",
                gap: "20px",
                overflowX: "auto",
              }}
            >
              {COLUMN_STATUSES.map((status) => (
                <SortableContext
                  key={status}
                  items={columns[status].map((t) => t.id)}
                  strategy={verticalListSortingStrategy}
                >
                  <DroppableColumn
                    id={status}
                    titulo={status.replace(/_/g, " ").toLowerCase()}
                    tasks={columns[status]}
                    expandedTaskId={expandedTaskId}
                    selectedTaskDetails={selectedTaskDetails}
                    loadingDetails={loadingDetails}
                    detailsError={detailsError}
                    updatingStatus={updatingStatus}
                    updateStatusError={updateStatusError}
                    toggleExpand={toggleExpand}
                    handleStatusChange={handleStatusChange}
                    onDelete={(deletedId) =>
                      setTasks((prev) => prev.filter((t) => t.id !== deletedId))
                    }
                  />
                </SortableContext>
              ))}
            </div>
          </DndContext>
        </div>

        <footer
          style={{
            backgroundColor: "#34495e",
            color: "white",
            width: "100%",
            textAlign: "center",
            padding: "10px 0",
            zIndex: 1,
          }}
        >
          Projeto de Software Impacta - 2025 | Renato Ferreira - devbodegami@gmail.com
        </footer>
      </div>
    </>
  );
}
