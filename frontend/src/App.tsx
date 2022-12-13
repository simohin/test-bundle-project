import React from 'react';
import './App.css';
import {fonts, Global, reset, ThemeProvider, themes} from "@qiwi/pijma-desktop";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Main} from "./route/main";
import {Login} from "./route/login";

const App: React.FC = () => (
    <ThemeProvider theme={themes.orange}>
        <Global styles={[reset, fonts]}/>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Main/>}/>
                <Route path="/login" element={<Login/>}/>
            </Routes>
        </BrowserRouter>

    </ThemeProvider>
);
export default App
