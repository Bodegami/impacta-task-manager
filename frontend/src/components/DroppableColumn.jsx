import React from "react";
import { useDroppable } from "@dnd-kit/core";
import TaskCard from "./TaskCard";

export default function DroppableColumn({
  id,
  titulo,
  tasks,
  expandedTaskId,
  selectedTaskDetails,
  loadingDetails,
  detailsError,
  updatingStatus,
  updateStatusError,
  toggleExpand,
  handleStatusChange,
  onDelete,
}) {
  const { setNodeRef } = useDroppable({ id });

  return (
    <div
      ref={setNodeRef}
      style={{
        flex: 1,
        backgroundColor: "#ecf0f1",
        padding: "10px",
        borderRadius: "10px",
        minHeight: "500px",
        maxHeight: "80vh",
        overflowY: "auto",
        boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
        display: "flex",
        flexDirection: "column",
        gap: "15px", // espaçamento entre os cards
      }}
    >
      <h3
        style={{
          textTransform: "capitalize",
          fontSize: "1.2rem",
          marginBottom: "10px",
          textAlign: "center",
          color: "#2c3e50",
        }}
      >
        {titulo.replace("_", " ")}
      </h3>

      {tasks.length === 0 ? (
        <p style={{ textAlign: "center", color: "#7f8c8d" }}>Sem tarefas</p>
      ) : (
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
            onDelete={() => onDelete(task.id)}
          />
        ))
      )}
    </div>
  );
}
