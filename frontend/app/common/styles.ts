export const container = {
    section: 'xl:px-24 xl:mb-32 px-4 mb-16',
    main: {
        mobile: 'xl:hidden h-full min-h-screen mt-[76px]',
        desktop: 'xl:flex h-full max-w-[1230px] mx-auto min-h-fit flex-col hidden'
    },
    autentication: {
        desktop: {
            section: 'xl:flex relative w-full h-full min-h-screen hidden',
            sidebar: 'w-[27.5%] min-w-[400px]',
            main: 'w-[720px] flex flex-col justify-center pl-[160px] py-[30px]'
        },

        mobile: {
            section: 'xl:hidden relative w-full h-full min-h-screen flex items-center justify-center py-8',
            main: 'w-fit max-w-[500px] px-4 h-full flex flex-col justify-center'
        }
    }
}
