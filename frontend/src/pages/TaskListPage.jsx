import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

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
      const token = localStorage.getItem("token");
      if (!token) {
        alert("Usuário não autenticado!");
        return;
      }
      try {
        const response = await fetch("http://127.0.0.1:8080/tasks", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setTasks(data);
        } else {
          alert("Erro ao buscar tarefas.");
        }
      } catch (error) {
        console.error("Erro:", error);
        alert("Erro ao conectar com o servidor.");
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
        const token = localStorage.getItem("token");
        const response = await fetch(`http://127.0.0.1:8080/tasks/${taskId}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.ok) {
          const data = await response.json();
          setSelectedTaskDetails(data);
        } else {
          throw new Error("Erro ao buscar detalhes da tarefa.");
        }
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
        status
      };

      const token = localStorage.getItem("token");
      const response = await fetch(`http://127.0.0.1:8080/tasks/${taskId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(taskData),
      });
      if (response.ok) {
        setTasks((prevTasks) =>
          prevTasks.map((task) =>
            task.id === taskId ? { ...task, status: status } : task
          )
        );
        setSelectedTaskDetails((prevDetails) => ({
          ...prevDetails,
          status: status,
        }));
      } else {
        throw new Error("Erro ao atualizar o status da tarefa.");
      }
    } catch (error) {
      console.error("Erro ao atualizar status:", error);
      setUpdateStatusError("Erro ao atualizar o status.");
    } finally {
      setUpdatingStatus(false);
    }
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        minHeight: "100vh",
        backgroundImage: `url(/assets/post-its.jpeg)`,
        backgroundSize: "cover",
        backgroundPosition: "center",
        position: "relative",
      }}
    >
      <div
        style={{
          position: "absolute",
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
          }}
        >
          {tasks.length > 0 ? (
            tasks.map((task) => {
              const isExpanded = expandedTaskId === task.id;
              return (
                    <div
                      key={task.id}
                      onClick={(e) => {
                        // Evita que o clique em elementos interativos (como select) minimize o card
                        if (e.target.tagName === "SELECT" || e.target.tagName === "OPTION") {
                          e.stopPropagation();
                          return;
                        }
                        toggleExpand(task.id);
                      }}
                      style={{
                        position: "relative",
                        padding: isExpanded ? "20px" : "15px",
                        borderRadius: "10px",
                        backgroundColor: "#f9f9f9",
                        boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
                        textAlign: "center",
                        borderLeft: `5px solid ${
                          task.status === "DONE"
                            ? "#2ecc71"
                            : task.status === "TO_DO"
                            ? "#f39c12"
                            : task.status === "IN_PROGRESS"
                            ? "#e74c3c"
                            : "#D3D3D3"
                        }`,
                        cursor: "pointer",
                        transform: isExpanded ? "scale(1.05)" : "scale(1)",
                        height: isExpanded ? "auto" : "120px",
                        paddingBottom: isExpanded ? "20px" : "30px",
                      }}
                    >
                  <h3 style={{ marginBottom: "10px", color: "#333" }}>
                    {task.title}
                  </h3>
                  <p style={{ fontSize: "0.9rem", color: "#666" }}>
                    ID: {task.id}
                  </p>
                  <p
                    style={{
                      fontSize: "0.9rem",
                      fontWeight: "bold",
                      color: isExpanded ? "#000" : "#666",
                    }}
                  >
                    Status: {task.status.replace("_", " ")}
                  </p>

                  {isExpanded && (
                    <div
                      style={{
                        backgroundColor: "#fff",
                        boxShadow: "0px 10px 20px rgba(0, 0, 0, 0.1)",
                        padding: "15px",
                        marginTop: "10px",
                        borderRadius: "5px",
                        textAlign: "left",
                        animation: "fadeIn 0.3s ease-in-out",
                      }}
                    >
                      {loadingDetails ? (
                        <p>Carregando detalhes...</p>
                      ) : detailsError ? (
                        <p>Erro: {detailsError}</p>
                      ) : selectedTaskDetails ? (
                        <>
                          <p>
                            <strong>Descrição:</strong>{" "}
                            {selectedTaskDetails.description || "N/A"}
                          </p>
                          <p>
                            <strong>Data de Criação:</strong>{" "}
                            {selectedTaskDetails.created_at
                              ? formatarDataHora(selectedTaskDetails.created_at, "/")
                              : "N/A"}
                          </p>
                          <p>
                            <strong>Prazo:</strong>{" "}
                            {selectedTaskDetails.due_date
                              ? formatarDataHora(selectedTaskDetails.due_date, "/")
                              : "N/A"}
                          </p>
                          <p>
                            <strong>Prioridade:</strong>{" "}
                            {selectedTaskDetails.priority || "Normal"}
                          </p>

                          <div>
                            <strong>Alterar Status:</strong>
                            <select
                              value={selectedTaskDetails.status}
                              onChange={(e) => {
                                e.stopPropagation(); // Evita que o clique no select minimize o card
                                handleStatusChange(
                                  task.id,
                                  task.title,
                                  selectedTaskDetails.description,
                                  selectedTaskDetails.due_date,
                                  e.target.value
                                );
                              }}
                              disabled={updatingStatus}
                            >
                              <option value="BACKLOG">BACKLOG</option>
                              <option value="TO_DO">TO DO</option>
                              <option value="IN_PROGRESS">IN PROGRESS</option>
                              <option value="DONE">DONE</option>
                            </select>
                            {updatingStatus && <p>Atualizando...</p>}
                            {updateStatusError && (
                              <p style={{ color: "red" }}>
                                {updateStatusError}
                              </p>
                            )}
                          </div>
                        </>
                      ) : null}
                    </div>
                  )}
                </div>
              );
            })
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
  );
}

function formatarDataHora(dataISO, separator) {
  try {
    dataISO = new String(dataISO);
    const data = new Date(dataISO);

    if (isNaN(data.getTime())) {
      return "Data inválida";
    }

    const dia = String(data.getDate()).padStart(2, "0");
    const mes = String(data.getMonth() + 1).padStart(2, "0");
    const ano = data.getFullYear();
    const hora = String(data.getHours()).padStart(2, "0");
    const minutos = String(data.getMinutes()).padStart(2, "0");

    return `${dia}${separator}${mes}${separator}${ano} ${hora}:${minutos}`;
  } catch (error) {
    console.error("Erro ao formatar data:", error);
    return "Erro ao formatar data";
  }
}
                    
                    