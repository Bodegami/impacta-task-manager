import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

export default function TaskListPage() {
  const navigate = useNavigate();
  const [tasks, setTasks] = useState([]);

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
      {/* Overlay com transparência */}
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
          Bem-vindo(a) à sua lista de tarefas. Aqui você verá todas as suas
          tarefas.
        </p>
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            width: "100%",
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

        {/* Grid de Tarefas */}
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fill, minmax(250px, 1fr))",
            gap: "20px",
            marginTop: "20px",
            width: "100%",
          }}
        >
          {tasks.length > 0 ? (
            tasks.map((task) => (
              <div
                key={task.id}
                style={{
                  padding: "15px",
                  borderRadius: "8px",
                  backgroundColor: "#f9f9f9",
                  boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
                  textAlign: "center",
                  borderLeft: `5px solid ${
                    task.status === "DONE"
                      ? "#2ecc71"
                      : task.status === "IN PROGRESS"
                      ? "#f39c12"
                      : "#e74c3c"
                  }`,
                }}
              >
                <h3 style={{ marginBottom: "10px", color: "#333" }}>
                  {task.title}
                </h3>
                <p style={{ fontSize: "0.9rem", color: "#666" }}>
                  ID: {task.id}
                </p>
                <p style={{ fontSize: "0.9rem", fontWeight: "bold" }}>
                  Status: {task.status}
                </p>
              </div>
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
  );
}
