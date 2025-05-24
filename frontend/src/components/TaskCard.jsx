import React from "react";
import { useNavigate } from "react-router-dom";
import { formatarDataHora } from "../utils/dateUtils";
import { FaTrash, FaEdit } from "react-icons/fa";
import { deleteTask } from "../services/taskService";
import "./TaskCard.css";

const TaskCard = ({
  task,
  isExpanded,
  selectedTaskDetails,
  loadingDetails,
  detailsError,
  updatingStatus,
  updateStatusError,
  toggleExpand,
  handleStatusChange,
  onDelete,
}) => {
  const navigate = useNavigate();

  const handleDelete = async (e) => {
    e.stopPropagation();
    if (window.confirm("Tem certeza que deseja excluir esta tarefa?")) {
      try {
        await deleteTask(task.id);
        onDelete(task.id);
      } catch (error) {
        alert("Erro ao excluir a tarefa.");
      }
    }
  };

  const handleEdit = (e) => {
    e.stopPropagation();
    navigate(`/tasks/edit/${task.id}`);
  };

  return (
    <div
      className={`task-card ${isExpanded ? "expanded" : ""} status-${task.status.toLowerCase()}`}
      key={task.id}
      onClick={(e) => {
        if (e.target.tagName === "SELECT" || e.target.tagName === "OPTION") {
          e.stopPropagation();
          return;
        }
        toggleExpand(task.id);
      }}
    >
      {isExpanded && (
        <>
          {/* Botão Editar */}
          <button
            className="task-card-btn edit"
            onClick={handleEdit}
            title="Editar Tarefa"
          >
            <FaEdit />
          </button>

          {/* Botão Excluir */}
          <button
            className="task-card-btn delete"
            onClick={handleDelete}
            title="Excluir Tarefa"
          >
            <FaTrash />
          </button>
        </>
      )}

      <h3 className="task-card-title">
        {task.title.toUpperCase()}
      </h3>
      <p className={`task-card-id ${isExpanded ? "expanded" : ""}`}>
        <strong>ID:</strong> {task.id}
      </p>
      <p className={`task-card-status ${isExpanded ? "expanded" : ""}`}>
        <strong>Status:</strong> {task.status.replace("_", " ")}
      </p>

      {isExpanded && (
        <div className="task-card-details">
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
                <strong>Criado em:</strong>{" "}
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
                <strong>Status: </strong>
                <select
                  value={selectedTaskDetails.status}
                  onChange={(e) => {
                    e.stopPropagation();
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
                  <p style={{ color: "red" }}>{updateStatusError}</p>
                )}
              </div>
            </>
          ) : null}
        </div>
      )}
    </div>
  );
};

export default TaskCard;