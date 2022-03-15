import React from 'react';
import AuthService from "../services/auth.service";
import './GlobalCss/Profile.css';
import { Button, Box, Tab, Divider, Stack,
    Skeleton, Accordion, AccordionSummary,
    Typography, AccordionDetails, Tooltip,
    Dialog, DialogActions, DialogContent,
    DialogTitle, TextField, Zoom } from '@mui/material';
import { TabContext, TabList, TabPanel } from '@mui/lab';
import DeleteIcon from '@mui/icons-material/Delete';
import SaveIcon from '@mui/icons-material/Save';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import AccountCircle from '@mui/icons-material/AccountCircle';

export default function ProfileCard() {
    const currentUser = AuthService.getCurrentUser();
    // console.log(currentUser);
    //Tabs_
    const [value, setValue] = React.useState('1');
    const handleChange = (event, newValue) => {
        setValue(newValue);
    }
    //Edit profile dialog
    const [open, setOpen] = React.useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    }
    return (
        //Profile
        <div className = "profile-card">
            <div className = "profile-upper">
                <div className = "profile-pic">
                    <img src="https://cdn140.picsart.com/297361716279211.png" alt='' height ="100px" width="100px"/>
                </div>
            </div>
            <div className = "profile-lower">
                {currentUser ? (<h3>{currentUser.username}</h3>) : <h3>Unknown user</h3>}
                {currentUser ? (<h4>{currentUser.roles}</h4>) : <h4>Unknown user role</h4>}
                <Box sx={{width: '100%', typography: 'body1'}}>
                    <TabContext value={value}>
                        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <TabList onChange={handleChange} centered aria-label="lab API tabs example">
                                <Tab label="About You" value="1" />
                                <Tab label="Posts" value="2" />
                            </TabList>
                        </Box>
                        <TabPanel value="1">
                            {currentUser ? (<p>Id: {currentUser.id}</p>) : <p>Unknown user id</p>}
                            {currentUser ? (<p>Email: {currentUser.email}</p>) : <p>Unknown user email</p>}
                        </TabPanel>
                        <TabPanel value="2">
                            <Stack spacing={1}>
                                <Skeleton variant="text" />
                                <Skeleton variant="circular" width={40} height={40} />
                                <Skeleton variant="rectangular" width={210} height={118} />
                            </Stack>

                        </TabPanel>
                    </TabContext>
                </Box>
                <Divider />
                <Box height = {16}/>
                <Stack spacing={2} direction="row" justifyContent = "space-evenly">
                    <Button
                        type = "submit"
                        variant = "contained"
                        color = "primary"
                        size = "medium"
                        onClick={handleClickOpen}
                    >
                        Edit Profile
                    </Button>
                    <Button
                        type = "submit"
                        variant = "contained"
                        color = "primary"
                        size = "medium"
                    >
                        Log out
                    </Button>
                </Stack>
                <Box height = {16}/>
                {/* Edit Profile Dialog */}
                <Dialog open={open} onClose={handleClose} >
                    <DialogTitle>Edit Profile</DialogTitle>
                    <DialogContent >
                        <Accordion>
                            <AccordionSummary
                                expandIcon={<ExpandMoreIcon/>}
                                aria-controls="panel1a-content"
                                id="panel1a-header"
                            >
                                <Typography>General Details</Typography>
                            </AccordionSummary>
                            <AccordionDetails>
                                <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                                    <AccountCircle sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                                    <TextField
                                        name = "username"
                                        id = "input-with-sx"
                                        label = "Username"
                                        variant = "standard"
                                        color = "primary"
                                        fullWidth
                                    />
                                </Box>
                                <Box height = {16}/>
                                <TextField
                                    name = "email"
                                    type = "input"
                                    variant = "standard"
                                    color = "primary"
                                    label = "Email"
                                    fullWidth
                                />
                                <Box height = {32}/>
                            </AccordionDetails>
                        </Accordion>
                        <Accordion>
                            <AccordionSummary
                                expandIcon={<ExpandMoreIcon/>}
                                aria-controls="panel1a-content"
                                id="panel1a-header"
                            >
                                <Typography>Password</Typography>
                            </AccordionSummary>
                            <AccordionDetails>
                                <TextField
                                    name = "password"
                                    type = "input"
                                    variant = "standard"
                                    color = "primary"
                                    label = "New password"
                                    fullWidth
                                />
                                <Box height = {16}/>
                                <TextField
                                    name = "password"
                                    type = "input"
                                    variant = "standard"
                                    color = "primary"
                                    label = "Confirm password"
                                    fullWidth
                                />
                                <Box height = {32}/>
                            </AccordionDetails>
                        </Accordion>
                        <Accordion>
                            <AccordionSummary
                                expandIcon={<ExpandMoreIcon/>}
                                aria-controls="panel1a-content"
                                id="panel1a-header"
                            >
                                <Typography>Personal Details</Typography>
                            </AccordionSummary>
                            <AccordionDetails>
                                <TextField
                                    name = "postcode"
                                    type = "input"
                                    variant = "standard"
                                    color = "primary"
                                    label = "Postcode"
                                    fullWidth
                                />
                                <Box height = {32}/>
                            </AccordionDetails>
                        </Accordion>
                    </DialogContent>
                    <DialogActions>
                        <Stack spacing={2} direction="row">
                            <Tooltip
                                title = "WARNING: This action cannot be undone"
                                arrow
                                TransitionComponent={ Zoom }
                            >
                                <Button
                                    type = "submit"
                                    variant = "outlined"
                                    color = "error"
                                    size = "medium"
                                    startIcon={<DeleteIcon />}
                                >
                                    Delete
                                </Button>
                            </Tooltip>
                            <Button
                                type = "submit"
                                variant = "outlined"
                                color = "primary"
                                size = "small"
                                onClick={handleClose}
                            >
                                Cancel
                            </Button>
                            <Button
                                type = "submit"
                                variant = "outlined"
                                color = "success"
                                size = "small"
                                onClick={handleClose}
                                startIcon={<SaveIcon />}
                            >
                                Save
                            </Button>
                        </Stack>
                    </DialogActions>
                    <Box height = {16}/>
                </Dialog>
            </div>
        </div>
    )
}