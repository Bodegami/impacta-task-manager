import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { loginUser } from "../services/userService";
import "./LoginPage.css";

export default function LoginPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const credentials = { email, password };
      await loginUser(credentials);
      navigate("/tasks");
    } catch (error) {
      alert(error.message || "Ocorreu um erro");
    }
  };

  return (
    <div className="login-container">
      <div className="login-overlay"></div>

      <div className="login-content">
        <h1 className="login-title">Login</h1>
        <form onSubmit={handleSubmit} className="login-form">
          <input 
            type="email" 
            placeholder="Email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
            required 
            className="login-input"
          />
          <input 
            type="password" 
            placeholder="Password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            required 
            className="login-input"
          />
          <button 
            type="submit"
            className="login-submit-btn"
            onMouseOver={(e) => e.target.style.backgroundColor = "#2980b9"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#3498db"}
          >
            Login
          </button>
        </form>
      </div>

      <footer className="default-footer">
        Projeto de Software Impacta - 2025 | Renato Ferreira - devbodegami@gmail.com
      </footer>
    </div>
  );
}
