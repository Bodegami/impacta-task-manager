export const normalizeStatus = (inputStatus) => {
    const input = inputStatus.toLowerCase().replace(/_/g, '');
  
    if (input.includes('back') || input.includes('log')) {
      return 'BACKLOG';
    }
    
    if (input.includes('prog') || input.includes('inprogress') || input.includes('in progress')) {
      return 'IN_PROGRESS';
    }
    
    if (input.includes('tod') || input.includes('todo') || input.includes('to do')) {
      return 'TO_DO';
    }
    
    if (input.includes('don')) {
      return 'DONE';
    }
  
    return inputStatus.toUpperCase(); // retorna o status original em maiúsculo se não encontrar correspondência
  };