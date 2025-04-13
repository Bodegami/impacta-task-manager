import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { fetchTaskDetails, updateTask } from "../services/taskService";

export default function EditTaskPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [loading, setLoading] = useState(true);

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [status, setStatus] = useState("");

  const [newTitle, setNewTitle] = useState("");
  const [newDescription, setNewDescription] = useState("");
  const [newDueDate, setNewDueDate] = useState("");
  const [newStatus, setNewStatus] = useState("");

  // Função para ajustar a data para o fuso horário de São Paulo
  const adjustDateToSaoPaulo = (dateString) => {
    const date = new Date(dateString);
    const offset = -3 * 60; // Offset de São Paulo em minutos (GMT-3)
    const utc = date.getTime() + (date.getTimezoneOffset() * 60000);
    const saoPauloDate = new Date(utc + (offset * 60000));

    return saoPauloDate;
  };

  const formatDateForInput = (date) => { // Alterado para receber um objeto Date
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
  };

  const formatToBackend = (dateString) => {
    const date = new Date(dateString);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    return `${day}-${month}-${year} ${hours}:${minutes}`;
  };

  useEffect(() => {
    const loadTask = async () => {
      try {
        const task = await fetchTaskDetails(id);
        setTitle(task.title);
        setDescription(task.description);
        // Ajustamos a data para São Paulo antes de formatar
        const saoPauloDueDate = adjustDateToSaoPaulo(task.due_date);
        setDueDate(formatDateForInput(saoPauloDueDate));
        setStatus(task.status);
        setLoading(false);
      } catch (error) {
        alert("Erro ao carregar a tarefa.");
        navigate("/tasks");
      }
    };
    loadTask();
  }, [id, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const taskData = {
        title: newTitle || title,
        description: newDescription || description,
        dueDate: formatToBackend(newDueDate || dueDate),
        status: newStatus || status,
      };
      await updateTask(id, taskData);
      alert("Tarefa atualizada com sucesso!");
      navigate("/tasks");
    } catch (error) {
      alert("Erro ao atualizar a tarefa.");
    }
  };

  if (loading) {
    return <div style={{ padding: "20px", textAlign: "center" }}>Carregando...</div>;
  }

  return (
    <div style={{
        display: "flex",
        flexDirection: "column",
        minHeight: "100vh",
        overflow: "hidden",
        backgroundImage: `url(/assets/post-its.jpeg)`,
        backgroundSize: "cover",
        backgroundPosition: "center",
        position: "relative",
        maxWidth: "100%", // Alterado de 100vw para 100%
        width: "100%",    // Alterado de 100vw para 100%
        boxSizing: "border-box"
    }}>
      {/* Overlay */}
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
        zIndex: 1,
        padding: "40px"
      }}>
        <h1 style={{
          fontSize: "2.5rem",
          marginBottom: "20px",
          color: "#333"
        }}>
          Editar Tarefa
        </h1>
        <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "10px", width: "300px" }}>
          <input
            type="text"
            placeholder={title}
            value={newTitle}
            onChange={(e) => setNewTitle(e.target.value)}
            style={{ padding: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
          />
          <textarea
            placeholder={description}
            value={newDescription}
            onChange={(e) => setNewDescription(e.target.value)}
            style={{ padding: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
          />
          <input
            type="datetime-local"
            value={newDueDate || dueDate}
            onChange={(e) => setNewDueDate(e.target.value)}
            style={{ padding: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
          />
          <select
            value={newStatus || status}
            onChange={(e) => setNewStatus(e.target.value)}
            style={{ padding: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
          >
            <option value="BACKLOG">BACKLOG</option>
            <option value="TO_DO">TO DO</option>
            <option value="IN_PROGRESS">IN PROGRESS</option>
            <option value="DONE">DONE</option>
          </select>
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
            Confirmar Edição
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

      <div style={{ marginTop: "auto", zIndex: 1 }}>
        <footer style={{
          backgroundColor: "#34495e",
          color: "white",
          width: "100vw",
          textAlign: "center",
          padding: "10px 0"
        }}>
          Projeto de Software Impacta - 2025 | Renato Ferreira - devbodegami@gmail.com
        </footer>
      </div>
    </div>
  );
}