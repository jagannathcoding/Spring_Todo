import { Navigate, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import Dashboard from './pages/Dashboard';
import useAuthUser from './hooks/useAuthUser';
import { Toaster } from 'react-hot-toast';

const App = () => {
    const { isLoading, authUser } = useAuthUser();
    const isAuthenticated = Boolean(authUser);

    if (isLoading) return <div className="flex items-center justify-center h-screen">Loading...</div>;

    return (
        <>
            <Routes>
                <Route
                    path="/"
                    element={isAuthenticated ? <Dashboard userName={authUser.name} /> : <Navigate to="/login" />}
                />
                <Route
                    path="/signup"
                    element={!isAuthenticated ? <SignupPage /> : <Navigate to="/" />}
                />
                <Route
                    path="/login"
                    element={!isAuthenticated ? <LoginPage /> : <Navigate to="/" />}
                />
            </Routes>
            <Toaster />
        </>
    );
};

export default App;
