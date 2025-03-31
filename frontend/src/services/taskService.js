export async function fetchTasksFromBackend() {
    const token = localStorage.getItem("token");
    if (!token) {
      throw new Error("Usuário não autenticado!");
    }
  
    try {
      const response = await fetch("http://127.0.0.1:8080/tasks", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
  
      if (!response.ok) {
        throw new Error("Erro ao buscar tarefas.");
      }
  
      return await response.json();
    } catch (error) {
      console.error("Erro ao conectar com o servidor:", error);
      throw error;
    }
}

export async function fetchTaskDetails(taskId) {
    const token = localStorage.getItem("token");
    if (!token) {
      throw new Error("Usuário não autenticado!");
    }
  
    try {
      const response = await fetch(`http://127.0.0.1:8080/tasks/${taskId}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
  
      if (!response.ok) {
        throw new Error("Erro ao buscar detalhes da tarefa.");
      }
  
      return await response.json();
    } catch (error) {
      console.error("Erro ao conectar com o servidor:", error);
      throw error;
    }
}

export async function updateTask(taskId, taskData) {
    const token = localStorage.getItem("token");
    if (!token) {
      throw new Error("Usuário não autenticado!");
    }
  
    try {
      const response = await fetch(`http://127.0.0.1:8080/tasks/${taskId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(taskData),
      });
  
      if (!response.ok) {
        throw new Error("Erro ao atualizar o status da tarefa.");
      }
  
      return await response.json(); // Retorna os dados atualizados, se necessário
    } catch (error) {
      console.error("Erro ao conectar com o servidor:", error);
      throw error;
    }
}