import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { fetchTaskDetails, updateTask } from "../services/taskService";
import "./EditTaskPage.css";

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
    return <div className="loading">Carregando...</div>;
  }

  return (
    <div className="edit-task-container">
      <div className="edit-task-overlay"></div>

      <div className="edit-task-content">
        <h1 className="edit-task-title">Editar Tarefa</h1>
        <form onSubmit={handleSubmit} className="edit-task-form">
          <input
            type="text"
            placeholder={title}
            value={newTitle}
            onChange={(e) => setNewTitle(e.target.value)}
            className="edit-task-input"
          />
          <textarea
            placeholder={description}
            value={newDescription}
            onChange={(e) => setNewDescription(e.target.value)}
            className="edit-task-input"
          />
          <input
            type="datetime-local"
            value={newDueDate || dueDate}
            onChange={(e) => setNewDueDate(e.target.value)}
            className="edit-task-input"
          />
          <select
            value={newStatus || status}
            onChange={(e) => setNewStatus(e.target.value)}
            className="edit-task-input"
          >
            <option value="BACKLOG">BACKLOG</option>
            <option value="TO_DO">TO DO</option>
            <option value="IN_PROGRESS">IN PROGRESS</option>
            <option value="DONE">DONE</option>
          </select>
          <button
            type="submit"
            className="edit-task-submit-btn"
            onMouseOver={(e) => e.target.style.backgroundColor = "#2980b9"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#3498db"}
          >
            Confirmar Edição
          </button>
        </form>
        <button
          onClick={() => navigate("/tasks")}
          className="edit-task-back-btn"
          onMouseOver={(e) => e.target.style.backgroundColor = "#27ae60"}
          onMouseOut={(e) => e.target.style.backgroundColor = "#2ecc71"}
        >
          Voltar
        </button>
      </div>

      <div className="edit-task-footer">
        <footer className="default-footer">
          Projeto de Software Impacta - 2025 | Renato Ferreira - devbodegami@gmail.com
        </footer>
      </div>
    </div>
  );
}