import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { getTodos, logout } from "../lib/api";
import AddTodoForm from "../components/AddTodoForm";
import useMarkCompleted from "../components/markCompleted";
import { toast } from "react-hot-toast";

const Dashboard = ({ userName }) => {
    const queryClient = useQueryClient();

    const { data: todos = [], isLoading, error } = useQuery({
        queryKey: ["todos"],
        queryFn: getTodos,
    });

    const markCompletedMutation = useMarkCompleted();

    const { mutate: logoutMutation } = useMutation({
        mutationFn: logout,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["authUser"] });
            toast.success("Logged out successfully");
        },
        onError: () => {
            toast.error("Failed to log out");
        },
    });

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-4">
            <h2 className="text-2xl font-bold mb-2">Welcome, {userName}!</h2>
            <p className="mb-4">This is your dashboard.</p>

            <button onClick={() => logoutMutation()} className="btn btn-error mb-6">
                Logout
            </button>

            <AddTodoForm />

            <div className="w-full max-w-md">
                <h3 className="text-lg font-semibold mb-2">Your Todos:</h3>

                {isLoading ? (
                    <p>Loading todos...</p>
                ) : error ? (
                    <p className="text-red-500">Failed to load todos. Add some to see them!</p>
                ) : todos.length === 0 ? (
                    <p className="text-gray-500">No todos yet. Start by adding one!</p>
                ) : (
                    <ul className="space-y-2">
                        {todos.map((todo) => (
                            <li
                                key={todo.id}
                                className="flex justify-between items-center bg-white p-3 rounded shadow"
                            >
                                <span className={todo.completed ? "line-through text-gray-500" : ""}>
                                    {todo.title}
                                </span>
                                {!todo.completed && (
                                    <button
                                        className="btn btn-sm btn-success"
                                        onClick={() => markCompletedMutation.mutate(todo.id)}
                                    >
                                        Mark as done
                                    </button>
                                )}
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
};

export default Dashboard;
