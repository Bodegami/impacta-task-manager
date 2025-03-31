import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { fetchTasksFromBackend, fetchTaskDetails, updateTask } from "../services/taskService";
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

      // Atualiza o estado local após a atualização bem-sucedida
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

          <div
            style={{ display: "flex", justifyContent: "space-between", width: "100%" }}
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
              gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))",
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
