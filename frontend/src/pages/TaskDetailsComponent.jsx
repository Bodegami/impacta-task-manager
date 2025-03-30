// import { useState, useEffect } from "react";

// const useFetchTaskDetails = (taskId) => {
//   const [taskDetails, setTaskDetails] = useState(null);
//   const [loading, setLoading] = useState(true);
//   const [error, setError] = useState(null);

//   useEffect(() => {
//     if (!taskId) return;

//     const fetchTask = async () => {
//       setLoading(true);
//       setError(null);
//       const token = localStorage.getItem("token");

//       try {
//         const response = await fetch(`http://127.0.0.1:8080/tasks/${taskId}`, {
//           method: "GET",
//           headers: {
//             "Content-Type": "application/json",
//             Authorization: `Bearer ${token}`,
//           },
//         });

//         if (!response.ok) {
//           throw new Error("Erro ao buscar detalhes da tarefa");
//         }

//         const data = await response.json();
//         setTaskDetails(data);
//       } catch (err) {
//         setError(err.message);
//       } finally {
//         setLoading(false);
//       }
//     };

//     fetchTask();
//   }, [taskId]);

//   return { taskDetails, loading, error };
// };