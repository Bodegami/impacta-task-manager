import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { createUser } from "../services/userService";
import "./SignupPage.css";

export default function SignupPage() {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (password !== confirmPassword) {
      alert("As senhas não coincidem!");
      return;
    }

    try {
      const userData = { name, email, password };
      const data = await createUser(userData);
      console.log("Usuário criado:", data);
      navigate("/tasks");
    } catch (error) {
      alert(error.message || "Ocorreu um erro.");
    }
  };

  return (
    <div className="signup-container">
      <div className="signup-overlay"></div>

      <div className="signup-content">
        <h1 className="signup-title">Cadastro</h1>
        <form onSubmit={handleSubmit} className="signup-form">
          <input 
            type="text" 
            placeholder="Nome" 
            value={name} 
            onChange={(e) => setName(e.target.value)} 
            required 
            className="signup-input"
          />
          <input 
            type="email" 
            placeholder="Email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
            required 
            className="signup-input"
          />
          <input 
            type="password" 
            placeholder="Senha" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            required 
            className="signup-input"
          />
          <input 
            type="password" 
            placeholder="Confirmar Senha" 
            value={confirmPassword} 
            onChange={(e) => setConfirmPassword(e.target.value)} 
            required 
            className="signup-input"
          />
          <button 
            type="submit"
            className="signup-submit-btn"
            onMouseOver={(e) => e.target.style.backgroundColor = "#2980b9"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#3498db"}
          >
            Cadastrar
          </button>
        </form>
      </div>

      <footer className="default-footer">
        Projeto de Software Impacta - 2025 | Renato Ferreira - devbodegami@gmail.com
      </footer>
    </div>
  );
}
