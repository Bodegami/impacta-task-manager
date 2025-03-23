import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useState } from "react";
import LoginPage from "./pages/LoginPage";
// import Dashboard from "./pages/Dashboard";
// import NotFound from "./pages/NotFound";
import HomePage from "./pages/HomePage";
import SignupPage from "./pages/SignupPage";
import TaskListPage from "./pages/TaskListPage";
import CreateTaskPage from "./pages/CreateTaskPage";

export default function App() {
  const [user, setUser] = useState(null);

  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage setUser={setUser} />} />
        <Route path="/login" element={<LoginPage setUser={setUser} />} />
        <Route path="/signup" element={<SignupPage setUser={setUser} />} />
        <Route path="/tasks" element={<TaskListPage setUser={setUser} />} />
        <Route path="/tasks/create" element={<CreateTaskPage setUser={setUser} />} />
        {/* <Route path="/dashboard" element={user ? <Dashboard user={user} /> : <LoginPage setUser={setUser} />} /> */}
        {/* <Route path="*" element={<NotFound />} /> */}
      </Routes>
    </Router>
  );
}