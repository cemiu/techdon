import React, { useState } from "react";
import Grid from "@material-ui/core/Grid";

import Topbar from "./topbar";
import Search from "./search";
import Item from "./item";

import "../App.css";

const items = [
    {
        name: "Computer",
    },
    {
        name: "USB",
    },
    {
        name: "Laptop",
    }
];

export default function Apply() {
    const [itemsInCart, setItemsInCart] = useState(0);
    const [searchText, setSearchText] = useState("");

    const handleAddItemToCart = () => setItemsInCart(itemsInCart + 1);

    const handleClearCart = () => setItemsInCart(0);

    const handleSearchChange = (e) => {
        setSearchText(e.target.value);
    };

    return (
        <div className="App">
            <header className="App-header">
                <Topbar count={itemsInCart} handleClearCart={handleClearCart} />

                <Search handleSearchChange={handleSearchChange} />
                <Grid container justify="center" spacing={4}>
                    {items
                        .filter((item) => item.name.includes(searchText))
                        .map(({ name }) => (
                            <Grid item>
                                <Item
                                    key={name}
                                    name={name}
                                    addItemToShoppingCart={handleAddItemToCart}
                                />
                            </Grid>
                        ))}
                </Grid>
            </header>
        </div>
    );
}
