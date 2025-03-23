import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function CreateTaskPage() {
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [dueDate, setDueDate] = useState("");

  // Função para formatar a data para "dd-MM-yyyy HH:mm"
  const formatDueDate = (dateString) => {
    const date = new Date(dateString);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    return `${day}-${month}-${year} ${hours}:${minutes}`;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = localStorage.getItem("token");
    if (!token) {
      alert("Usuário não autenticado!");
      return;
    }

    const taskData = {
      title,
      description,
      dueDate: formatDueDate(dueDate)
    };

    try {
      const response = await fetch("http://127.0.0.1:8080/tasks", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(taskData),
      });

      if (response.ok) {
        alert("Tarefa criada com sucesso!");
        navigate("/tasks");
      } else {
        alert("Erro ao criar tarefa.");
      }
    } catch (error) {
      console.error("Erro:", error);
      alert("Erro ao conectar com o servidor.");
    }
  };

  return (
    <div style={{ 
      display: "flex", 
      flexDirection: "column", 
      justifyContent: "center", 
      alignItems: "center", 
      minHeight: "100vh", 
      backgroundImage: `url(/assets/post-its.jpeg)`,
      backgroundSize: "cover",
      backgroundPosition: "center",
      position: "relative"
    }}>
      {/* Overlay com transparência */}
      <div style={{
        position: "absolute",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: "rgba(255, 255, 255, 0.8)",
        zIndex: 0
      }}></div>

      <div style={{ 
        display: "flex", 
        flexDirection: "column", 
        alignItems: "center", 
        justifyContent: "center", 
        flexGrow: 1,
        zIndex: 1
      }}>
        <h1 style={{ 
          fontSize: "2.5rem", 
          marginBottom: "20px", 
          color: "#333" 
        }}>
          Criar Nova Tarefa
        </h1>
        <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "10px", width: "300px" }}>
          <input
            type="text"
            placeholder="Título"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
            style={{ padding: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
          />
          <textarea
            placeholder="Descrição"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
            style={{ padding: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
          />
          <input
            type="datetime-local"
            value={dueDate}
            onChange={(e) => setDueDate(e.target.value)}
            required
            style={{ padding: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
          />
          <button
            type="submit"
            style={{ 
              padding: "10px", 
              borderRadius: "5px", 
              border: "none", 
              backgroundColor: "#3498db", 
              color: "white", 
              cursor: "pointer",
              transition: "background-color 0.3s"
            }}
            onMouseOver={(e) => e.target.style.backgroundColor = "#2980b9"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#3498db"}
          >
            Criar Tarefa
          </button>
        </form>
        <button
          onClick={() => navigate("/tasks")}
          style={{ 
            marginTop: "10px", 
            padding: "10px", 
            borderRadius: "5px", 
            border: "none", 
            backgroundColor: "#2ecc71", 
            color: "white", 
            cursor: "pointer",
            transition: "background-color 0.3s"
          }}
          onMouseOver={(e) => e.target.style.backgroundColor = "#27ae60"}
          onMouseOut={(e) => e.target.style.backgroundColor = "#2ecc71"}
        >
          Voltar
        </button>
      </div>

      <footer style={{ 
        backgroundColor: "#34495e", 
        color: "white", 
        width: "100%", 
        textAlign: "center", 
        padding: "10px 0", 
        zIndex: 1
      }}>
        Projeto de Software Impacta - 2025 | Renato Ferreira - devbodegami@gmail.com
      </footer>
    </div>
  );
}