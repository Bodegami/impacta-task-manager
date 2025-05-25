export function formatarDataHora(dataString, separator) {
    try {
      // Tenta criar um objeto Date a partir da string ISO 8601
      const data = new Date(dataString);
  
      // Verifica se o objeto Date é válido
      if (isNaN(data.getTime())) {
        // Se for inválido, retorna a mensagem de erro
        console.error("String de data inválida fornecida:", dataString);
        return "Data inválida";
      }
  
      // Extrai as partes da data e hora
      const dia = String(data.getDate()).padStart(2, "0");
      const mes = String(data.getMonth() + 1).padStart(2, "0"); // getMonth() é base 0
      const ano = data.getFullYear();
      const hora = String(data.getHours()).padStart(2, "0");
      const minutos = String(data.getMinutes()).padStart(2, "0");
  
      return `${dia}${separator}${mes}${separator}${ano} ${hora}:${minutos}`;
    } catch (error) {
      console.error("Erro inesperado ao formatar data:", error);
      return "Erro interno ao formatar data";
    }
}

