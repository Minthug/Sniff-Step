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
        opacityChange(document.getElementById('poppy-foot2'), '0.7', 600)
        opacityChange(document.getElementById('poppy-foot3'), '0.8', 800)
        opacityChange(document.getElementById('poppy-foot4'), '1', 1000)
    }, [])

    return (
        <div className="relative h-[calc(100vh-112px)] flex gap-[100px] px-[100px] select-none">
            <div className="relative flex-1">
                <div>
                    <img
                        id="poppy-foot1"
                        className="absolute bottom-[23%] right-[5%] -rotate-[80deg] w-[30px] object-contain opacity-[0] duration-500"
                        src="/images/poppy-foot.png"
                        alt=""
                    />
                    <img
                        id="poppy-foot2"
                        className="absolute bottom-[25%] right-[30%] -rotate-[90deg] w-[30px] object-contain opacity-[0] duration-500"
                        src="/images/poppy-foot.png"
                        alt=""
                    />
                    <img
                        id="poppy-foot3"
                        className="absolute bottom-[23%] left-[30%] -rotate-[80deg] w-[30px] object-contain opacity-[0] duration-500"
                        src="/images/poppy-foot.png"
                        alt=""
                    />
                    <img
                        id="poppy-foot4"
                        className="absolute bottom-[25%] left-[5%] -rotate-[100deg] w-[30px] object-contain opacity-[0] duration-500"
                        src="/images/poppy-foot.png"
                        alt=""
                    />
                </div>
                <div
                    style={{
                        opacity: loading ? 0 : 1,
                        transform: loading ? 'translateY(20px)' : 'translateY(0)'
                    }}
                    className="h-full flex justify-center flex-col gap-2 pb-[100px] text-[60px] font-[800] z-10 transition-all duration-500"
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
            </div>
            <div className="flex-1 flex justify-center items-center">
                <img className="object-contain" src="/images/main/1-section.png" alt="1-section" />
            </div>
        </div>
    )
}
