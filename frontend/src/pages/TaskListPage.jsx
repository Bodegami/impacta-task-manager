import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import {
  fetchTasksFromBackend,
  fetchTaskDetails,
  updateTask,
  fetchTasksByFilter
} from "../services/taskService";
import { formatarDataHora } from "../utils/dateUtils";
import TaskCard from "../components/TaskCard";

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

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const data = await fetchTasksFromBackend();
        setTasks(data);
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

    try {
      dueDate = formatarDataHora(dueDate, "-");

      const taskData = {
        title,
        description,
        dueDate,
        status,
      };

      await updateTask(taskId, taskData);

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

  const buscarTarefasFiltradas = async () => {
    if (!filtroValor.trim()) {
      alert("Digite um valor para buscar.");
      return;
    }
  
    try {
      const data = await fetchTasksByFilter(filtroCampo, filtroValor);
      console.log("🔍 Resposta do backend (tarefas filtradas):", data);
      setTasks(data);
      setExpandedTaskId(null);
      setSelectedTaskDetails(null);
    } catch (error) {
      console.error("❌ Erro ao buscar tarefas filtradas:", error);
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
            width: "80%",
          }}
        >
          <h1 style={{ fontSize: "2.5rem", marginBottom: "20px", color: "#333" }}>
            Lista de Tarefas
          </h1>
          <p
            style={{
              fontSize: "1.2rem",
              marginBottom: "20px",
              color: "#555",
              textAlign: "center",
            }}
          >
            Clique em uma tarefa para ver mais detalhes.
          </p>

          {/* Filtro */}
          <div
            style={{
              marginTop: "20px",
              backgroundColor: "#f5f5f5",
              padding: "20px",
              borderRadius: "10px",
              width: "100%",
              boxShadow: "0 0 10px rgba(0,0,0,0.1)",
            }}
          >
            <h3 style={{ marginBottom: "10px", marginTop: "10px" }}>Filtrar Tarefas</h3>
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

          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              width: "100%",
              marginTop: "20px"
            }}
          >
            <button
              onClick={() => navigate("/tasks/create")}
              style={{
                padding: "10px 20px",
                borderRadius: "5px",
                border: "none",
                backgroundColor: "#3498db",
                color: "white",
                cursor: "pointer",
                transition: "background-color 0.3s",
              }}
              onMouseOver={(e) => (e.target.style.backgroundColor = "#2980b9")}
              onMouseOut={(e) => (e.target.style.backgroundColor = "#3498db")}
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
                transition: "background-color 0.3s",
              }}
              onMouseOver={(e) => (e.target.style.backgroundColor = "#2980b9")}
              onMouseOut={(e) => (e.target.style.backgroundColor = "#3498db")}
            >
              Voltar para Home
            </button>
          </div>

          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(auto-fit, minmax(250px, 300px))", // limite de 300px
              justifyContent: "center", // centraliza os cards
              gap: "20px",
              marginTop: "20px",
              width: "100%",
              alignItems: "start",
              paddingBottom: "100px"
            }}
          >
            {tasks.length > 0 ? (
              tasks.map((task) => (
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
                  onDelete={(deletedId) =>
                    setTasks((prev) => prev.filter((t) => t.id !== deletedId))
                  }
                />
              ))
            ) : (
              <p>Nenhuma tarefa encontrada.</p>
            )}
          </div>
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
          Projeto de Software Impacta - 2025 | Renato Ferreira -
          devbodegami@gmail.com
        </footer>
      </div>
    </>
  );
}