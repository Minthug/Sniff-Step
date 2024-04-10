import { container } from '@/app/common'
import { Board } from '@/app/types/board'
import React from 'react'

interface Props {
    boards: Board[]
}

export function Desktop({ boards }: Props) {
    return (
        <div className={`${container.main.desktop} items-center px-[108px]`}>
            <div className="my-[60px] text-[32px] font-[500]">현재 등록된 게시글</div>
            <div className="max-w-[1600px] grid grid-cols-3 gap-8">
                {boards.map((board) => (
                    <div key={board.id} className="relative w-[304px] h-full shadow-sm rounded-md cursor-pointer bg-white">
                        <div
                            className={`
                                    hover:bg-black
                                    active:opacity-[0.16]
                                    absolute w-full h-full opacity-[0.08] z-10 rounded-md
                                `}
                        />
                        <div className="relative h-[304px] mb-2">
                            <img className="w-full h-full rounded-md rounded-b-none object-cover" src={board.imageUrl} />
                        </div>
                        <div className="flex gap-4 px-4 pb-4">
                            <img
                                className="w-[40px] h-[40px] border rounded-full"
                                src={board.profileUrl || '/logo1-removebg-preview.png'}
                            />
                            <div className="flex flex-col justify-center">
                                <div className="w-[170px] whitespace-nowrap overflow-hidden text-ellipsis text-[14px]">{board.title}</div>
                                <div className="text-[12px]">{board.address}</div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}
