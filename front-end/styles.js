import { makeStyles } from '@material-ui/core/styles';

export default makeStyles((theme) => ({
    root: {
        // maxWidth: 345, original width style
        maxWidth: '345',

    },
    media: {
        height: 50,

        paddingTop: '56.25%', // 16:9
    },
    cardActions: {
        display: 'flex',
        justifyContent: 'flex-end',
    },
    cardContent: {
        display: 'flex',
        justifyContent: 'space-between',
    },
    toolbar: theme.mixins.toolbar,
    content: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.default,
        padding: theme.spacing(3),
    },

}));
