export function formatarDataHora(dataISO, separator) {
    try {
      dataISO = new String(dataISO);
      const data = new Date(dataISO);
  
      if (isNaN(data.getTime())) {
        return "Data inválida";
      }
  
      const dia = String(data.getDate()).padStart(2, "0");
      const mes = String(data.getMonth() + 1).padStart(2, "0");
      const ano = data.getFullYear();
      const hora = String(data.getHours()).padStart(2, "0");
      const minutos = String(data.getMinutes()).padStart(2, "0");
  
      return `${dia}${separator}${mes}${separator}${ano} ${hora}:${minutos}`;
    } catch (error) {
      console.error("Erro ao formatar data:", error);
      return "Erro ao formatar data";
    }
}

