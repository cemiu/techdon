


import React from 'react';
import Product from './Product';
import Grid from '@material-ui/core/Grid';


export default function Main(props) {
    const { products, onAdd } = props;
    return (
        <main className="block col-2" >
            <Grid container ="center" spacing={2}>
                {products.map((product) => (
                    <Grid key={product.id} item xs={12} sm={6} md={4} lg={3}>
                        <Product key={product.id} product={product} onAdd={onAdd} />
                    </Grid>


                ))}
            </Grid>
        </main>
    );
}