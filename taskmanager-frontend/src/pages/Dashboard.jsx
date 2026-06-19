import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';
import CreateTaskForm from '../components/CreateTaskForm';
import {
    AppBar, Toolbar, Typography, Button, Container,
    Grid, Card, CardContent, CardActions, Chip,
    Dialog, DialogTitle, DialogContent, Fab, Box
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';

export default function Dashboard({ setToken }) {

    const [tasks, setTasks] = useState([]);
    const [error, setError] = useState('');
    const [open, setOpen] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                const response = await api.get('/tasks');
                setTasks(response.data);
            } catch (error) {
                setError('Failed to fetch tasks');
            }
        };
        fetchTasks();
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        setToken(null);
        navigate('/login');
    };

    const handleDelete = async (id) => {
        try {
            await api.delete(`/tasks/${id}`);
            setTasks(tasks.filter((task) => task.id !== id));
        } catch (error) {
            setError('Failed to delete task');
        }
    };

    const handleTaskCreated = (newTask) => {
        setTasks([...tasks, newTask]);
        setOpen(false);
    };

    return (
        <Box sx={{ flexGrow: 1 }}>
            {/* Navbar */}
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" sx={{ flexGrow: 1 }}>
                        Task Manager
                    </Typography>
                    <Button color="inherit" onClick={handleLogout}>Logout</Button>
                </Toolbar>
            </AppBar>

            {/* Main content */}
            <Container sx={{ marginTop: 4 }}>
                {/* Task cards go here */}
                <Grid container spacing={3}>
                    {tasks.map((task) => (
                        <Grid item xs={12} sm={6} md={4} key={task.id}>
                            <Card>
                                <CardContent>
                                    <Typography variant="h6">{task.title}</Typography>
                                    <Typography variant="body2" color="text.secondary">{task.description}</Typography>
                                    <Box sx={{ display: 'flex', gap: 1, marginTop: 2 }}>
                                        <Chip label={task.status} color="primary" size="small" />
                                        <Chip label={task.priority} color="secondary" size="small" />
                                    </Box>
                                    <Typography variant="body2" sx={{ marginTop: 1 }}>
                                        Due: {new Date(task.dueAt).toLocaleDateString()}
                                    </Typography>
                                </CardContent>
                                <CardActions>
                                    <Button size="small" color="error" onClick={() => handleDelete(task.id)}>
                                        Delete
                                    </Button>
                                </CardActions>
                            </Card>
                        </Grid>
                    ))}
                </Grid>

                {/* Floating action button to open modal */}
                <Fab color="primary" sx={{ position: 'fixed', bottom: 32, right: 32 }}
                     onClick={() => setOpen(true)}>
                    <AddIcon />
                </Fab>

                {/* Create task modal */}
                <Dialog open={open} onClose={() => setOpen(false)} fullWidth maxWidth="sm">
                    <DialogTitle>Create New Task</DialogTitle>
                    <DialogContent>
                        <CreateTaskForm setToken={setToken} onTaskCreated={handleTaskCreated} />
                    </DialogContent>
                </Dialog>
            </Container>
        </Box>
    );
}