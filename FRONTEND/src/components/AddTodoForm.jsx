import { useMutation, useQueryClient } from "@tanstack/react-query";
import { addTodo } from "../lib/api";
import { useState } from "react";
import { toast } from "react-hot-toast";

const AddTodoForm = () => {
    const [newTodo, setNewTodo] = useState("");
    const queryClient = useQueryClient();

    const { mutate: addTodoMutation } = useMutation({
        mutationFn: addTodo,
        onSuccess: () => {
            toast.success("Todo added!");
            queryClient.invalidateQueries({ queryKey: ["todos"] });
            setNewTodo("");
        },
        onError: () => {
            toast.error("Failed to add todo");
        },
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        if (newTodo.trim()) {
            addTodoMutation(newTodo);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="mb-6 flex gap-2 w-full max-w-md">
            <input
                type="text"
                placeholder="New todo title"
                value={newTodo}
                onChange={(e) => setNewTodo(e.target.value)}
                className="input input-bordered flex-1"
            />
            <button type="submit" className="btn btn-primary">
                Add
            </button>
        </form>
    );
};

export default AddTodoForm;
