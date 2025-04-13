import React from "react";
import { useNavigate } from "react-router-dom";
import { formatarDataHora } from "../utils/dateUtils";
import { FaTrash, FaEdit } from "react-icons/fa"; // Ícones de lixeira e edição
import { deleteTask } from "../services/taskService";

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
      key={task.id}
      onClick={(e) => {
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
      {isExpanded && (
        <>
          {/* Botão Editar */}
          <button
            onClick={handleEdit}
            style={{
              position: "absolute",
              top: "10px",
              right: "45px",
              background: "transparent",
              border: "none",
              cursor: "pointer",
              color: "#2980b9",
              fontSize: "1.2rem",
              marginRight: "5px",
            }}
            title="Editar Tarefa"
          >
            <FaEdit />
          </button>

          {/* Botão Excluir */}
          <button
            onClick={handleDelete}
            style={{
              position: "absolute",
              top: "10px",
              right: "10px",
              background: "transparent",
              border: "none",
              cursor: "pointer",
              color: "#c0392b",
              fontSize: "1.2rem",
            }}
            title="Excluir Tarefa"
          >
            <FaTrash />
          </button>
        </>
      )}

      <h3 style={{ marginBottom: "10px", color: "#333" }}>
        {task.title.toUpperCase()}
      </h3>
      <p style={{ fontSize: "0.9rem", color: "#666" }}>ID: {task.id}</p>
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
