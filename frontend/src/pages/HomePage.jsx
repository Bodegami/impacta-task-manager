import { useNavigate } from "react-router-dom";

export default function HomePage() {
  const navigate = useNavigate();

  return (
    <div style={{ 
      display: "flex", 
      flexDirection: "column", 
      alignItems: "center", 
      justifyContent: "center", 
      height: "100vh", 
      backgroundColor: "white" 
    }}>
      <div style={{ 
        display: "flex", 
        flexDirection: "column", 
        alignItems: "center", 
        justifyContent: "center", 
        transform: "translateY(-20%)" 
      }}>
        <h1>Task Manager</h1>
        <div>
          <button style={{ margin: "10px" }} onClick={() => navigate("/signup")}>Signup</button>
          <button style={{ margin: "10px" }} onClick={() => navigate("/login")}>Signin</button>
        </div>
      </div>
    </div>
  );
}

