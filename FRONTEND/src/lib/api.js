import { axiosInstance } from "./axios";

export const signup = async (signupData) => {
    const response = await axiosInstance.post("/auth/signup", signupData);
    return response.data;
};

export const login = async (loginData) => {
    const response = await axiosInstance.post("/auth/login", loginData);
    return response.data;
};

export const logout = async () => {
    const response = await axiosInstance.post("/auth/logout");
    return response.data;
};

export const getAuthUser = async () => {
    try {
        const res = await axiosInstance.get("/auth/me");
        return res.data;
    } catch (error) {
        console.log("Error in getAuthUser ", error);
        return null;
    }
};

export const getTodos = async () => {
    const res = await axiosInstance.get("/todos");
    return res.data;
};

export const addTodo = async (newTodo) => {
    const res = await axiosInstance.post("/todos", { title: newTodo });
    return res.data;
};

export const markTodoCompleted = async (id) => {
    const res = await axiosInstance.put(`/todos/${id}/complete`);
    return res.data;
};
