import { useNavigate } from "react-router-dom";
import { useState } from "react";

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
      const response = await fetch("http://127.0.0.1:8080/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ name, email, password }),
      });

      if (response.ok) {
        const data = await response.json();
        console.log("Usuário criado:", data);
        navigate("/tasks");
      } else {
        alert("Falha ao criar a conta.");
      }
    } catch (error) {
      console.error("Erro:", error);
      alert("Ocorreu um erro.");
    }
  };

  return (
    <div style={{ 
      display: "flex", 
      flexDirection: "column", 
      justifyContent: "space-between", 
      alignItems: "center", 
      minHeight: "100vh", 
      backgroundImage: `url(/assets/post-its.jpeg)`,  // Caminho da imagem
      backgroundSize: "cover",
      backgroundPosition: "center",
      position: "relative"
    }}>
      {/* Overlay com transparência */}
      <div style={{
        position: "absolute",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: "rgba(255, 255, 255, 0.8)",
        zIndex: 0
      }}></div>

      <div style={{ 
        display: "flex", 
        flexDirection: "column", 
        alignItems: "center", 
        justifyContent: "center", 
        flexGrow: 1,
        zIndex: 1
      }}>
        <h1 style={{ 
          fontSize: "2.5rem", 
          marginBottom: "20px", 
          color: "#333" 
        }}>
          Cadastro
        </h1>
        <form 
          onSubmit={handleSubmit} 
          style={{ 
            display: "flex", 
            flexDirection: "column", 
            gap: "10px", 
            backgroundColor: "rgba(255, 255, 255, 0.9)", 
            padding: "20px", 
            borderRadius: "10px", 
            boxShadow: "0 0 10px rgba(0, 0, 0, 0.2)"
          }}
        >
          <input 
            type="text" 
            placeholder="Nome" 
            value={name} 
            onChange={(e) => setName(e.target.value)} 
            required 
            style={{ 
              padding: "10px", 
              borderRadius: "5px", 
              border: "1px solid #ccc" 
            }}
          />
          <input 
            type="email" 
            placeholder="Email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
            required 
            style={{ 
              padding: "10px", 
              borderRadius: "5px", 
              border: "1px solid #ccc" 
            }}
          />
          <input 
            type="password" 
            placeholder="Senha" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            required 
            style={{ 
              padding: "10px", 
              borderRadius: "5px", 
              border: "1px solid #ccc" 
            }}
          />
          <input 
            type="password" 
            placeholder="Confirmar Senha" 
            value={confirmPassword} 
            onChange={(e) => setConfirmPassword(e.target.value)} 
            required 
            style={{ 
              padding: "10px", 
              borderRadius: "5px", 
              border: "1px solid #ccc" 
            }}
          />
          <button 
            type="submit"
            style={{ 
              padding: "10px 20px", 
              borderRadius: "5px", 
              border: "none", 
              backgroundColor: "#3498db", 
              color: "white", 
              cursor: "pointer",
              transition: "background-color 0.3s"
            }}
            onMouseOver={(e) => e.target.style.backgroundColor = "#2980b9"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#3498db"}
          >
            Cadastrar
          </button>
        </form>
      </div>

      <footer style={{ 
        backgroundColor: "#34495e", 
        color: "white", 
        width: "100%", 
        textAlign: "center", 
        padding: "10px 0", 
        zIndex: 1
      }}>
        Projeto de Software Impacta - 2025 | Renato Ferreira - devbodegami@gmail.com
      </footer>
    </div>
  );
}
