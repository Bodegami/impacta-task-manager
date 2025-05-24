import { useNavigate } from "react-router-dom";
import "./HomePage.css";

export default function HomePage() {
  const navigate = useNavigate();

  return (
    <div className="home-container">
      <div className="home-overlay"></div>

      <div className="home-content">
        <h1 className="home-title">Task Manager</h1>
        <div className="home-buttons">
          <button 
            className="home-signup-btn"
            onClick={() => navigate("/signup")}
            onMouseOver={(e) => e.target.style.backgroundColor = "#2980b9"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#3498db"}
          >
            Signup
          </button>
          <button 
            className="home-signin-btn"
            onClick={() => navigate("/login")}
            onMouseOver={(e) => e.target.style.backgroundColor = "#27ae60"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#2ecc71"}
          >
            Signin
          </button>
        </div>
      </div>

      <footer className="default-footer">
        Projeto de Software Impacta - 2025 | Renato Ferreira - devbodegami@gmail.com
      </footer>
    </div>
  );
}
