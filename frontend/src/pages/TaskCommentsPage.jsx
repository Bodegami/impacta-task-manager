import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchTaskDetails, fetchTaskComments, createTaskComment } from '../services/taskService';
import { formatarDataHora } from '../utils/dateUtils';
import './TaskCommentsPage.css';

const TaskCommentsPage = () => {
  const { taskId } = useParams();
  const navigate = useNavigate();
  const [task, setTask] = useState(null);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchTaskAndComments = async () => {
      try {
        const taskData = await fetchTaskDetails(taskId);
        setTask(taskData);
        const commentsData = await fetchTaskComments(taskId);
        console.log("Dados de comentários recebidos:", commentsData);
        setComments(commentsData);
      } catch (err) {
        setError('Erro ao carregar os dados da tarefa e comentários');
        console.error("Erro ao buscar dados:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchTaskAndComments();
  }, [taskId]);

  const handleSubmitComment = async (e) => {
    e.preventDefault();
    if (!newComment.trim()) return;

    try {
      await createTaskComment(taskId, newComment);
      const updatedComments = await fetchTaskComments(taskId);
      console.log("Dados de comentários atualizados recebidos:", updatedComments);
      setComments(updatedComments);
      setNewComment('');
    } catch (err) {
      setError('Erro ao enviar comentário');
      console.error("Erro ao criar comentário:", err);
    }
  };

  if (loading) {
    return (
      <div className="task-comments-container">
        <div className="task-comments-overlay" />
        <div className="task-comments-content">
          <p className="loading">Carregando...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="task-comments-container">
        <div className="task-comments-overlay" />
        <div className="task-comments-content">
          <p className="error">{error}</p>
          <button className="back-btn" onClick={() => navigate(-1)}>
            Voltar
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="task-comments-container">
      <div className="task-comments-overlay" />
      <div className="task-comments-content">
        <h1 className="task-comments-title">Comentários da Tarefa</h1>

        <div className="task-details">
          <h2>Detalhes da Tarefa</h2>
          <p><strong>ID:</strong> {task.id}</p>
          <p><strong>Título:</strong> {task.title}</p>
          <p><strong>Descrição:</strong> {task.description}</p>
          <p><strong>Status:</strong> {task.status}</p>
          <p><strong>Prioridade:</strong> {task.priority}</p>
          <p><strong>Data de Criação:</strong> {task.created_at ? formatarDataHora(task.created_at, "/") : "Data indisponível"}</p>
          <p><strong>Prazo:</strong> {task.due_date ? formatarDataHora(task.due_date, "/") : "Não definido"}</p>
        </div>

        <div className="comments-section">
          <h3>Comentários</h3>
          <form className="comment-form" onSubmit={handleSubmitComment}>
            <textarea
              className="comment-input"
              value={newComment}
              onChange={(e) => setNewComment(e.target.value)}
              placeholder="Digite seu comentário..."
              required
            />
            <button type="submit" className="comment-submit-btn">
              Enviar Comentário
            </button>
          </form>

          <div className="comments-list">
            {comments.map((comment) => (
              <div key={comment.id} className="comment-card">
                <div className="comment-header">
                  <span className="comment-author">{comment.userName.toUpperCase() || "Autor Desconhecido"}</span>
                  <span className="comment-date">
                    {comment.createdAt ? formatarDataHora(comment.createdAt, "/") : "Data indisponível"}
                  </span>
                </div>
                <p className="comment-text">{comment.comment || "Sem conteúdo"}</p>
              </div>
            ))}
          </div>
        </div>

        <button className="back-btn" onClick={() => navigate(-1)}>
          Voltar
        </button>
      </div>
    </div>
  );
};

export default TaskCommentsPage; 