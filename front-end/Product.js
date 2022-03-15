

import React from 'react';
import useStyles from './styles';
import { Card, CardMedia, CardContent, Typography } from '@material-ui/core'

export default function Product(props) {
    const { product, onAdd } = props;
    const classes = useStyles();




    return (
        <div>
            <Card className={classes.root}>

                <CardMedia className={classes.media} title={product.name} image={product.image} />
                <CardContent>
                    <div className={classes.cardContent}>
                        <Typography gutterBottom variant="h5" component="h2">
                            {product.name}
                        </Typography>

                        <Typography variant="h5" component="h2" >
                            {product.type}

                        </Typography>
                    </div>
                    <Typography variant="body2" color="textSecondary" > {product.description}</Typography>
                    <div>
                        <button onClick={() => onAdd(product)}>Apply</button>
                    </div>
                </CardContent>
            </Card>
        </div>
    );
}





