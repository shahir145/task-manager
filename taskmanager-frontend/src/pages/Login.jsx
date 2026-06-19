import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';
import { Container, Box, TextField, Button, Typography, Alert } from '@mui/material';

export default function Login({ setToken }) {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/auth/login', { username, password });
            localStorage.setItem('token', response.data.token);
            setToken(response.data.token);
            navigate('/dashboard');
        } catch (error) {
            setError(error.response?.data || 'Login failed');
        }
    }

    return (
        <Container maxWidth="xs">
            <Box sx={{
                marginTop: 8,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                gap: 2
            }}>
                <Typography variant="h4">Task Manager</Typography>
                <Typography variant="subtitle1">Sign in to your account</Typography>
                {error && <Alert severity="error" sx={{ width: '100%' }}>{error}</Alert>}
                <Box component="form" onSubmit={handleSubmit} sx={{ width: '100%', display: 'flex', flexDirection: 'column', gap: 2 }}>
                    <TextField
                        label="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        fullWidth
                        required
                    />
                    <TextField
                        label="Password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        fullWidth
                        required
                    />
                    <Button type="submit" variant="contained" fullWidth size="large">
                        Login
                    </Button>
                    <Button variant="text" fullWidth onClick={() => navigate('/register')}>
                        Don't have an account? Register
                    </Button>
                </Box>
            </Box>
        </Container>
    );
}