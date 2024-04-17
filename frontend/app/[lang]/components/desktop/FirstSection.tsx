import { D2CodingBold } from '@/app/fonts'
import React, { useEffect, useState } from 'react'

export function FirstSection() {
    const [loading, setLoading] = useState(true)

    function opacityChange(element: HTMLElement | null, opacity: string, time: number) {
        setTimeout(() => {
            if (element) {
                element.style.opacity = opacity
            }
        }, time)
    }

    useEffect(() => {
        setLoading(false)
        opacityChange(document.getElementById('poppy-foot1'), '0.6', 400)
        opacityChange(document.getElementById('poppy-foot2'), '0.7', 400)
        opacityChange(document.getElementById('poppy-foot3'), '0.8', 400)
        opacityChange(document.getElementById('poppy-foot4'), '1', 400)
    }, [])

    return (
        <div className="relative h-[calc(100vh-112px)] flex select-none">
            <div
                style={{
                    opacity: loading ? 0 : 1,
                    top: loading ? '140px' : '120px'
                }}
                className={`
            absolute top-[120px] left-[100px] flex flex-col gap-2 text-[60px] font-[800] z-10 transition-all duration-500
        `}
            >
                <div>반려견을 위한</div>
                <div>우리 동네 산책 커뮤니티</div>
                <button
                    className={`
                ${D2CodingBold.className}
                hover:bg-green-600 hover:text-gray-100
                active:bg-green-700
                w-full py-4 mt-4 bg-[#10b94e] rounded-lg font-[500] text-[18px] text-white
        `}
                >
                    산책인 공고 구경하기
                </button>
            </div>
            <img className="absolute top-1/2 right-[80px] -translate-y-1/2 w-[600px] object-contain" src="/images/main-1-6.png" alt="" />
            <img
                id="poppy-foot1"
                className="absolute bottom-[20px] left-[700px] -translate-y-1/2 -rotate-[30deg] w-[60px] object-contain opacity-[0] duration-500"
                src="/images/poppy-foot.png"
                alt=""
            />
            <img
                id="poppy-foot2"
                className="absolute bottom-[50px] left-[530px] -translate-y-1/2 -rotate-[10deg] w-[60px] object-contain opacity-[0] duration-500"
                src="/images/poppy-foot.png"
                alt=""
            />
            <img
                id="poppy-foot3"
                className="absolute top-[100px] left-[550px] -translate-y-1/2 -rotate-[30deg] w-[60px] object-contain opacity-[0] duration-500"
                src="/images/poppy-foot.png"
                alt=""
            />
            <img
                id="poppy-foot4"
                className="absolute top-[50px] left-[380px] -translate-y-1/2 -rotate-[10deg] w-[60px] object-contain opacity-[0] duration-500"
                src="/images/poppy-foot.png"
                alt=""
            />
        </div>
    )
}
