// CreateTaskPage.jsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./CreateTaskPage.css";

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
    <div className="create-task-container">
      <div className="create-task-overlay"></div>

      <div className="create-task-content">
        <h1 className="create-task-title">Criar Nova Tarefa</h1>
        <form onSubmit={handleSubmit} className="create-task-form">
          <input
            type="text"
            placeholder="Título"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
            className="create-task-input"
          />
          <textarea
            placeholder="Descrição"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
            className="create-task-input"
          />
          <input
            type="datetime-local"
            value={dueDate}
            onChange={(e) => setDueDate(e.target.value)}
            required
            className="create-task-input"
          />
          <button
            type="submit"
            className="create-task-submit-btn"
            onMouseOver={(e) => e.target.style.backgroundColor = "#2980b9"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#3498db"}
          >
            Criar Tarefa
          </button>
        </form>
        <button
          onClick={() => navigate("/tasks")}
          className="create-task-back-btn"
          onMouseOver={(e) => e.target.style.backgroundColor = "#27ae60"}
          onMouseOut={(e) => e.target.style.backgroundColor = "#2ecc71"}
        >
          Voltar
        </button>
      </div>

      <footer className="default-footer">
        Projeto de Software Impacta - 2025 | Renato Ferreira - devbodegami@gmail.com
      </footer>
    </div>
  );
}