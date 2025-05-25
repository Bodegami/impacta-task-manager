import { normalizeStatus } from '../utils/statusUtils';

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

export async function deleteTask(taskId) {
  const token = localStorage.getItem("token");
  if (!token) {
    throw new Error("Usuário não autenticado!");
  }

  try {
    const response = await fetch(`http://127.0.0.1:8080/tasks/${taskId}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error("Erro ao excluir a tarefa.");
    }

    return true; // Ou qualquer resposta útil do backend
  } catch (error) {
    console.error("Erro ao excluir tarefa:", error);
    throw error;
  }
}

export async function searchTasks(paramKey, paramValue) {
  const token = localStorage.getItem("token");
  if (!token) {
    throw new Error("Usuário não autenticado!");
  }

  try {
    const url = new URL("http://127.0.0.1:8080/tasks/search");
    url.searchParams.append(paramKey, paramValue);

    const response = await fetch(url.toString(), {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error("Erro ao buscar tarefas com filtro.");
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao buscar tarefas com filtro:", error);
    throw error;
  }
}

export const fetchTasksByFilter = async (campo, valor) => {
  const token = localStorage.getItem("token");
  
  // Se o campo for status, normaliza o valor
  if (campo === 'status') {
    valor = normalizeStatus(valor);
  }
  
  const params = new URLSearchParams({ [campo]: valor });

  const response = await fetch(`http://127.0.0.1:8080/tasks/search?${params.toString()}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(`Erro na busca: ${response.status} - ${message}`);
  }

  const data = await response.json();
  return data;
};

export async function fetchTaskComments(taskId) {
  const token = localStorage.getItem("token");
  if (!token) {
    throw new Error("Usuário não autenticado!");
  }

  try {
    const response = await fetch(`http://127.0.0.1:8080/tasks/${taskId}/comments`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error("Erro ao buscar comentários da tarefa.");
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao buscar comentários:", error);
    throw error;
  }
}

export async function createTaskComment(taskId, comment) {
  const token = localStorage.getItem("token");
  if (!token) {
    throw new Error("Usuário não autenticado!");
  }

  try {
    const response = await fetch("http://127.0.0.1:8080/tasks/comments", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        taskId,
        comment,
      }),
    });

    if (!response.ok) {
      throw new Error("Erro ao criar comentário.");
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao criar comentário:", error);
    throw error;
  }
}