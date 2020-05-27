
import React from "react";
import { createMuiTheme } from "@material-ui/core";
import { ThemeProvider } from "@material-ui/styles";
import PostCard02 from './PostCard02';
import createOverrides from './theme';

const baseTheme = createMuiTheme();

const Demo = () => (
    // Normally, you need just one <ThemeProvider /> at root component
    <ThemeProvider
        theme={{
            ...baseTheme,
            overrides: createOverrides(baseTheme)
        }}
    >
        <PostCard02 />
    </ThemeProvider>
)

export default Demo