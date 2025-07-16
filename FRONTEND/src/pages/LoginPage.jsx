import React, { useState } from 'react';
import useLogin from '../hooks/useLogin';
import { Link } from 'react-router-dom';
import { toast } from 'react-hot-toast';

const LoginPage = () => {
  const [loginData, setLoginData] = useState({ email: "", password: "" });
  const { isPending, error, loginMutation } = useLogin();

  const handleLogin = (e) => {
    e.preventDefault();
    loginMutation(loginData, {
      onSuccess: () => {
        toast.success("Logged in successfully!");
      },
      onError: () => {
        toast.error("Invalid credentials");
      },
    });
  };

  return (
    <div className='h-screen flex items-center justify-center p-4'>
      <form onSubmit={handleLogin} className='bg-white p-8 rounded shadow-md w-full max-w-sm'>
        <h2 className='text-xl font-bold mb-4'>Login</h2>

        <input
          type="email"
          placeholder='Email'
          className='input input-bordered w-full mb-3'
          value={loginData.email}
          onChange={(e) => setLoginData({ ...loginData, email: e.target.value })}
          required
        />
        <input
          type="password"
          placeholder="Password"
          className="input input-bordered w-full mb-3"
          value={loginData.password}
          onChange={(e) => setLoginData({ ...loginData, password: e.target.value })}
          required
        />
        <button type="submit" className="btn btn-primary w-full" disabled={isPending}>
          {isPending ? "Logging in..." : "Login"}
        </button>
        <p className="mt-4 text-sm text-center">
          Don't have an account? <Link to="/signup" className="text-blue-500">Sign up</Link>
        </p>
      </form>
    </div>
  );
};

export default LoginPage;

