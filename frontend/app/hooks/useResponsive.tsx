import { useCallback, useEffect, useState } from 'react'

export default function useResponsive() {
    const [viewportWidth, setViewportWidth] = useState(0)
    const mobile = viewportWidth < 1280

    const handleResize = useCallback(() => {
        setViewportWidth(window.innerWidth)
    }, [])

    useEffect(() => {
        setViewportWidth(window.innerWidth)
        window.addEventListener('resize', handleResize)
        return () => {
            window.removeEventListener('resize', handleResize)
        }
    }, [])

    return { mobile }
}
