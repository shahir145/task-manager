import { useState } from 'react';
import api from '../api/axios';
import {
    FormControl, InputLabel, Select, MenuItem, TextField, Box, Button, Alert
} from '@mui/material';
import { DateTimePicker } from '@mui/x-date-pickers';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';

export default function CreateTaskForm({ setToken, onTaskCreated }) {

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [status, setStatus] = useState('TO_DO');
    const [priority, setPriority] = useState('LOW');
    const [dueAt, setDueAt] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        // call POST /tasks and pass onTaskCreated the new task
        e.preventDefault();
            try {
                const response = await api.post('/tasks', { title, description, status, priority, dueAt });
                onTaskCreated(response.data);
            } catch (error) {
                setError(error.response?.data || 'Task creation failed');
            }
    };

    return (
        <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField id="outlined-basic" value={title} onChange={(e) => setTitle(e.target.value)} label="Title" variant="outlined" />
            <TextField id="outlined-basic" value={description} onChange={(e) => setDescription(e.target.value)} label="Description" variant="outlined" />

            <FormControl fullWidth>
                <InputLabel>Status</InputLabel>
                <Select value={status} label="Status" onChange={(e) => setStatus(e.target.value)}>
                    <MenuItem value="TO_DO">To Do</MenuItem>
                    <MenuItem value="IN_PROGRESS">In Progress</MenuItem>
                    <MenuItem value="DONE">Done</MenuItem>
                </Select>
            </FormControl>

            <FormControl fullWidth>
                <InputLabel>Priority</InputLabel>
                <Select value={priority} label="Priority" onChange={(e) => setPriority(e.target.value)}>
                    <MenuItem value="LOW">Low</MenuItem>
                    <MenuItem value="MEDIUM">Medium</MenuItem>
                    <MenuItem value="HIGH">High</MenuItem>
                </Select>
            </FormControl>

            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DateTimePicker
                    label="Due Date"
                    value={dueAt ? dayjs(dueAt) : null}
                    onChange={(newValue) => setDueAt(newValue ? newValue.format('YYYY-MM-DDTHH:mm:ss') : '')}
                />
            </LocalizationProvider>

            <Button type="submit" variant="contained" fullWidth size="large">
                Create Task
            </Button>
            {error && <Alert severity="error" sx={{ width: '100%' }}>{error}</Alert>}
        </Box>
    );
}