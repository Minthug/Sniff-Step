export const container = {
    header: 'xl:px-16 px-2',
    section: 'xl:px-24 px-4 mb-32',
    main: {
        mobile: 'xl:hidden h-full min-h-screen mt-[76px]',
        desktop: 'xl:flex h-full min-h-screen flex-col hidden'
    },
    autentication: {
        desktop: {
            section: 'xl:flex relative w-full h-full min-h-screen hidden',
            sidebar: 'w-[27.5%] min-w-[400px]',
            main: 'pl-[160px] w-[720px] flex flex-col justify-center'
        },

        mobile: {
            section: 'xl:hidden relative w-full h-full min-h-screen flex items-center justify-center py-8',
            main: 'w-fit max-w-[500px] px-4 h-full flex flex-col justify-center'
        }
    }
}
