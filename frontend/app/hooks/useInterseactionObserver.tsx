import React, { useEffect } from 'react'

export function useIntersectionObserver(targets: React.RefObject<HTMLElement>[], threshold = 0.5) {
    useEffect(() => {
        const elements = targets.map((target) => target.current).filter((target) => target !== null && target !== undefined)
        if (elements.length === 0) {
            return
        }

        const observer = new IntersectionObserver(
            (entries) => {
                entries.forEach((entry) => {
                    const target = entry.target as HTMLElement
                    if (entry.isIntersecting) {
                        target.style.opacity = '1'
                    }
                })
            },
            { threshold }
        )

        elements.forEach((element) => observer.observe(element as HTMLElement))

        return () => {
            elements.forEach((element) => {
                if (element) observer.unobserve(element)
            })
        }
    }, [targets])
}
