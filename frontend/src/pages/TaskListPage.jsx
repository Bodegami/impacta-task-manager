import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export default function TaskListPage() {
  const navigate = useNavigate();

  useEffect(() => {
    // Simulando autenticação bem-sucedida e redirecionamento
    setTimeout(() => {
      navigate("/tasks");
    }, 1000);
  }, [navigate]);

  return (
    <div style={{ 
      display: "flex", 
      flexDirection: "column", 
      alignItems: "center", 
      justifyContent: "center", 
      height: "100vh", 
      backgroundColor: "white" 
    }}>
      <h1>Task List</h1>
      <p>Welcome to your task list. Here you will see all your tasks.</p>
    </div>
  );
}
