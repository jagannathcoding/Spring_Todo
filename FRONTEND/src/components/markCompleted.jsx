import { useMutation, useQueryClient } from "@tanstack/react-query";
import { markTodoCompleted } from "../lib/api";
import { toast } from "react-hot-toast";

const useMarkCompleted = () => {
    const queryClient = useQueryClient();

    const mutation = useMutation({
        mutationFn: (id) => markTodoCompleted(id),
        onSuccess: () => {
            toast.success("Marked as done!");
            queryClient.invalidateQueries({ queryKey: ["todos"] });
        },
        onError: () => {
            toast.error("Failed to mark as done");
        },
    });

    return mutation;
};

export default useMarkCompleted;
