import { useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { signup } from '../lib/api';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-hot-toast';

const SignupPage = () => {
  const [signupData, setSignupData] = useState({ name: "", email: "", password: "" });
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const { mutate: signupMutation, isPending } = useMutation({
    mutationFn: signup,
    onSuccess: () => {
      toast.success("Signup successful! Please login.");
      queryClient.invalidateQueries({ queryKey: ['authUser'] });
      navigate("/login");
    },
    onError: (error) => {
      toast.error(error.response?.data || "Something went wrong");
    },
  });

  const validateEmail = (email) => {
    return /\S+@\S+\.\S+/.test(email);
  };

  const handleSignup = (e) => {
    e.preventDefault();

    if (!signupData.name || !signupData.email || !signupData.password) {
      toast.error("All fields are required!");
      return;
    }

    if (!validateEmail(signupData.email)) {
      toast.error("Please enter a valid email address!");
      return;
    }

    signupMutation(signupData);
  };

  return (
    <div className='h-screen flex items-center justify-center p-4 bg-gray-50'>
      <form onSubmit={handleSignup} className='bg-white p-8 rounded shadow-md w-full max-w-sm'>
        <h2 className='text-xl font-bold mb-4'>Sign Up</h2>

        <input
          type="text"
          placeholder="Name"
          className="input input-bordered w-full mb-3"
          value={signupData.name}
          onChange={(e) => setSignupData({ ...signupData, name: e.target.value })}
        />

        <input
          type="text" // <== keep text so no browser popup!
          placeholder="Email"
          className="input input-bordered w-full mb-3"
          value={signupData.email}
          onChange={(e) => setSignupData({ ...signupData, email: e.target.value })}
        />

        <input
          type="password"
          placeholder="Password"
          className="input input-bordered w-full mb-3"
          value={signupData.password}
          onChange={(e) => setSignupData({ ...signupData, password: e.target.value })}
        />

        <button type="submit" className="btn btn-primary w-full" disabled={isPending}>
          {isPending ? "Signing up..." : "Sign Up"}
        </button>

        <p className="mt-4 text-sm text-center">
          Already have an account? <Link to="/login" className="text-blue-500">Login</Link>
        </p>
      </form>
    </div>
  );
};

export default SignupPage;
