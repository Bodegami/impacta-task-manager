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
    <div className="flex flex-col items-center justify-center min-h-screen bg-white">
      <h1 className="text-2xl font-bold mb-4">Criar Nova Tarefa</h1>
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <input
          type="text"
          placeholder="Título"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
          className="border p-2 rounded"
        />
        <textarea
          placeholder="Descrição"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
          className="border p-2 rounded"
        />
        <input
          type="datetime-local"
          value={dueDate}
          onChange={(e) => setDueDate(e.target.value)}
          required
          className="border p-2 rounded"
        />
        <button
          type="submit"
          className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          Criar Tarefa
        </button>
      </form>
      <button
        onClick={() => navigate("/tasks")}
        className="mt-4 px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600"
      >
        Voltar
      </button>
    </div>
  );
}
