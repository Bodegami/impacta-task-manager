import { useState } from "react";

export default function SignupPage() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: ""
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Form submitted", formData);
  };

  return (
    <div style={{ 
      display: "flex", 
      flexDirection: "column", 
      alignItems: "center", 
      justifyContent: "center", 
      height: "100vh", 
      backgroundColor: "white" 
    }}>
      <h1>Signup</h1>
      <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", width: "300px" }}>
        <input type="text" name="name" placeholder="Name" value={formData.name} onChange={handleChange} style={{ margin: "5px 0", padding: "10px" }} />
        <input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} style={{ margin: "5px 0", padding: "10px" }} />
        <input type="password" name="password" placeholder="Password" value={formData.password} onChange={handleChange} style={{ margin: "5px 0", padding: "10px" }} />
        <input type="password" name="confirmPassword" placeholder="Confirm Password" value={formData.confirmPassword} onChange={handleChange} style={{ margin: "5px 0", padding: "10px" }} />
        <button type="submit" style={{ marginTop: "10px", padding: "10px", cursor: "pointer" }}>Signup</button>
      </form>
    </div>
  );
}