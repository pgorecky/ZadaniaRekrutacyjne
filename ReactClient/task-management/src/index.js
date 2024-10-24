import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {ConfigProvider, theme} from "antd";
import CreateTaskPage from "./pages/CreateTaskPage";
import CreateUserPage from "./pages/CreateUserPage";
import TasksPage from "./pages/TasksPage";
import UsersPage from "./pages/UsersPage";
import UserPage from "./pages/UserPage";
import TaskPage from "./pages/TaskPage";
import EditUserPage from "./pages/EditUserPage";
import EditTaskPage from "./pages/EditTaskPage";


const root = ReactDOM.createRoot(document.getElementById('root'));

const router = createBrowserRouter([
    {
        path: '/',
        element: <App/>,
        children: [
            {
                path: '/task/create',
                element: <CreateTaskPage/>
            },
            {
                path: '/user/add',
                element: <CreateUserPage/>
            },
            {
                path: '/task/all',
                element: <TasksPage/>
            },
            {
                path: '/user/all',
                element: <UsersPage/>
            },
            {
                path: '/user/:id',
                element: <UserPage/>
            },
            {
                path: '/user/:id/edit',
                element: <EditUserPage/>
            },
            {
                path: '/task/:id',
                element: <TaskPage/>
            },
            {
                path: '/task/:id/edit',
                element: <EditTaskPage/>
            },
        ]
    }
])

root.render(
    <ConfigProvider
        theme={{
            token: {
                colorPrimary: '#F48668',
                colorLink: '#fff',
            },
            components: {
                Layout: {
                    bodyBg: '#262626',
                    footerBg: '#262626',
                    siderBg: '#141414',
                    triggerBg: '#141414',
                    triggerColor: '#F48668',
                },
            },
            algorithm: theme.darkAlgorithm,
        }}
    >
        <RouterProvider router={router}/>
    </ConfigProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
