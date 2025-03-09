import { useNavigate } from "react-router-dom";

export default function HomePage() {
  const navigate = useNavigate();

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
          Task Manager
        </h1>
        <div>
          <button 
            style={{ 
              margin: "10px", 
              padding: "10px 20px", 
              borderRadius: "5px", 
              border: "none", 
              backgroundColor: "#3498db", 
              color: "white", 
              cursor: "pointer",
              transition: "background-color 0.3s"
            }}
            onClick={() => navigate("/signup")}
            onMouseOver={(e) => e.target.style.backgroundColor = "#2980b9"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#3498db"}
          >
            Signup
          </button>
          <button 
            style={{ 
              margin: "10px", 
              padding: "10px 20px", 
              borderRadius: "5px", 
              border: "none", 
              backgroundColor: "#2ecc71", 
              color: "white", 
              cursor: "pointer",
              transition: "background-color 0.3s"
            }}
            onClick={() => navigate("/login")}
            onMouseOver={(e) => e.target.style.backgroundColor = "#27ae60"}
            onMouseOut={(e) => e.target.style.backgroundColor = "#2ecc71"}
          >
            Signin
          </button>
        </div>
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
