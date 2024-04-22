import type { Config } from 'tailwindcss'

const config: Config = {
    content: ['./pages/**/*.{js,ts,jsx,tsx,mdx}', './components/**/*.{js,ts,jsx,tsx,mdx}', './app/**/*.{js,ts,jsx,tsx,mdx}'],
    theme: {
        extend: {
            backgroundImage: {
                'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
                'gradient-conic': 'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))'
            }
        },
        keyframes: {
            leftBounce: {
                '0%, 100%': { transform: 'translateX(-15%)', animationTimingFunction: 'cubic-bezier(.58,.25,.83,.67)' },
                '50%': { transform: 'translateX(0)', animationTimingFunction: 'cubic-bezier(0, 0, 0, 1)' }
            },
            spin: { from: { transform: 'rotate(0deg)' }, to: { transform: 'rotate(360deg)' } }
        },
        animation: {
            leftBounce: 'leftBounce 1.2s infinite',
            spin: 'spin 1s linear infinite'
        }
    },
    mode: 'jit',
    plugins: []
}
export default config
