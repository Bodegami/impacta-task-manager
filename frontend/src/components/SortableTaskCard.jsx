import { useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import TaskCard from "./TaskCard";

export default function SortableTaskCard({
  task,
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
  const {
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
  } = useSortable({ id: task.id, data: { status: task.status } });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
    cursor: "grab",
  };

  return (
    <div ref={setNodeRef} style={style} {...attributes} {...listeners}>
      <TaskCard
        task={task}
        expandedTaskId={expandedTaskId}
        selectedTaskDetails={selectedTaskDetails}
        loadingDetails={loadingDetails}
        detailsError={detailsError}
        updatingStatus={updatingStatus}
        updateStatusError={updateStatusError}
        toggleExpand={toggleExpand}
        handleStatusChange={handleStatusChange}
        onDelete={onDelete}
      />
    </div>
  );
}
